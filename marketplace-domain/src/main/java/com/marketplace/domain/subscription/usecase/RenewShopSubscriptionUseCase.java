package com.marketplace.domain.subscription.usecase;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.payment.PaymentTokenRequest;
import com.marketplace.domain.payment.PaymentTokenResponse;
import com.marketplace.domain.payment.TCTPPaymentGatewayAdapter;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.subscription.RenewShopSubscriptionInput;
import com.marketplace.domain.subscription.ShopSubscription.Status;
import com.marketplace.domain.subscription.ShopSubscriptionDraft;
import com.marketplace.domain.subscription.ShopSubscriptionTime;
import com.marketplace.domain.subscription.SubscriptionPromo.ValueType;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDao;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDraftDao;
import com.marketplace.domain.subscription.dao.SubscriptionPlanDao;
import com.marketplace.domain.subscription.dao.SubscriptionPromoDao;

@Component
public class RenewShopSubscriptionUseCase {

	@Autowired
	private ShopDao shopDao;

	@Autowired
	private ShopSubscriptionDao shopSubscriptionDao;
	
	@Autowired
	private ShopSubscriptionDraftDao shopSubscriptionDraftDao;

	@Autowired
	private SubscriptionPlanDao subscriptionPlanDao;
	
	@Autowired
	private SubscriptionPromoDao subscriptionPromoDao;

	@Autowired
	private TCTPPaymentGatewayAdapter paymentGatewayAdapter;

	@Retryable(noRetryFor = { ApplicationException.class })
	@Transactional
	public PaymentTokenResponse apply(RenewShopSubscriptionInput data) {
		
		var shopId = data.getShopId();
		
		var shop = shopDao.findById(shopId);

		if (shop == null || shop.isDeleted()) {
			throw new ApplicationException("Shop not found");
		}
		
		if (shop.getStatus() != Shop.Status.APPROVED) {
			throw new ApplicationException("Shop not approved");
		}

		var subscriptionPlan = subscriptionPlanDao.findById(data.getSubscriptionPlanId());

		if (subscriptionPlan == null) {
			throw new ApplicationException("Subscription plan not found");
		}
		
		var shopSubscriptionDraft = new ShopSubscriptionDraft();
		shopSubscriptionDraft.setTitle(subscriptionPlan.getTitle());
		shopSubscriptionDraft.setSubTotalPrice(subscriptionPlan.getPrice());
		
		if (data.getPromoCodeId() != null) {
			if (!subscriptionPlan.isPromoUsable()) {
				throw new ApplicationException("Promo code cannot be used on this plan");
			}
			
			var promo = subscriptionPromoDao.findById(data.getPromoCodeId());
			if (promo == null) {
				throw new ApplicationException("Invalid promo code");
			}
			
			if (promo.isUsed()) {
				throw new ApplicationException("Promo code already used");
			}
			
			if (promo.getExpiredAt() < System.currentTimeMillis()) {
				throw new ApplicationException("Promo code expired");
			}
			
			if (promo.getMinConstraint() != null && promo.getMinConstraint().compareTo(subscriptionPlan.getPrice()) == 1) {
				throw new ApplicationException(String.format("Promo code must used on %f and above", promo.getMinConstraint()));
			}
			
			if (promo.getValueType() == ValueType.FIXED_AMOUNT) {
				shopSubscriptionDraft.setDiscount(promo.getValue());
			} else {
				var discount = subscriptionPlan.getPrice().multiply(promo.getValue()).divide(BigDecimal.valueOf(100));
				shopSubscriptionDraft.setDiscount(discount);
			}
			
			shopSubscriptionDraft.setPromoCode(promo.getCode());
			
			subscriptionPromoDao.updateUsed(promo.getId(), true);
		
		}

		shopSubscriptionDraft.setTotalPrice(shopSubscriptionDraft.getSubTotalPrice().subtract(shopSubscriptionDraft.getDiscount()));
		shopSubscriptionDraft.setDuration(subscriptionPlan.getDuration());
		shopSubscriptionDraft.setShop(shop);
		
		var draft = shopSubscriptionDraftDao.create(shopSubscriptionDraft);
		
		if (shopSubscriptionDraft.getTotalPrice().compareTo(BigDecimal.ZERO) != 1) {
			var latestSubscription = shopSubscriptionDao.findLatestSubscriptionByShop(shopId);
			var shopSubscription = draft.toShopSubscription();
			var time = new ShopSubscriptionTime();
			time.setInvoiceNo(shopSubscription.getInvoiceNo());
			time.setShopId(shopId);
			
			shopSubscription.setStatus(Status.SUCCESS);
			
			var duration = subscriptionPlan.getDuration();
			
			if (latestSubscription == null) {
				var start = LocalDate.now();

				time.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
				time.setEndAt(start.plusDays(duration - 1).atTime(LocalTime.MAX)
						.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
			} else {
				var start = Instant.ofEpochMilli(latestSubscription.getEndAt()).atZone(ZoneOffset.UTC).toLocalDate()
						.plusDays(1);

				time.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
				time.setEndAt(start.plusDays(duration - 1).atTime(LocalTime.MAX)
						.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());

			}
			
			shopSubscriptionDao.save(shopSubscription);
			shopSubscriptionDao.saveTime(time);
			shopDao.updateExpiredAt(shopId, time.getEndAt());

			return null;
		} else if (shopSubscriptionDraft.getTotalPrice().compareTo(BigDecimal.ZERO) == 1) {
			
			var paymentRequest = new PaymentTokenRequest();
			paymentRequest.setShopId(shopId);
			paymentRequest.setAmount(draft.getTotalPrice());
			paymentRequest.setInvoiceNo(String.valueOf(draft.getId()));
			paymentRequest.setDescription(
					String.format("%s %d days subscription", subscriptionPlan.getTitle(), subscriptionPlan.getDuration()));

			var paymentTokenResult = paymentGatewayAdapter.requestPaymentToken(paymentRequest);

			if (!"0000".equals(paymentTokenResult.getRespCode())) {
				throw new ApplicationException("Something went wrong. Please try again");
			}

			return paymentTokenResult;
		}

		throw new ApplicationException("Something went wrong. Please try again");
	}

}
