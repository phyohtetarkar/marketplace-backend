package com.shoppingcenter.domain.subscription.usecase;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.payment.PaymentGatewayAdapter;
import com.shoppingcenter.domain.payment.PaymentTokenRequest;
import com.shoppingcenter.domain.payment.PaymentTokenResponse;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.subscription.RenewShopSubscriptionInput;
import com.shoppingcenter.domain.subscription.ShopSubscription;
import com.shoppingcenter.domain.subscription.ShopSubscription.Status;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;
import com.shoppingcenter.domain.subscription.SubscriptionPromo.ValueType;
import com.shoppingcenter.domain.subscription.SubscriptionPromoDao;

import lombok.Setter;

@Setter
public class RenewShopSubscriptionUseCase {

	private ShopDao shopDao;

	private ShopMemberDao shopMemberDao;

	private ShopSubscriptionDao shopSubscriptionDao;

	private SubscriptionPlanDao subscriptionPlanDao;
	
	private SubscriptionPromoDao subscriptionPromoDao;

	private PaymentGatewayAdapter paymentGatewayAdapter;

	public PaymentTokenResponse apply(RenewShopSubscriptionInput data) {
		
		var shopId = data.getShopId();
		
		var shop = shopDao.findById(shopId);

		if (shop == null) {
			throw new ApplicationException("Shop not found");
		}

		var member = shopMemberDao.findByShopAndUser(shopId, data.getUserId());

		if (member == null) {
			throw new ApplicationException("Shop not found");
		}

		var subscription = subscriptionPlanDao.findById(data.getSubscriptionPlanId());

		if (subscription == null) {
			throw new ApplicationException("Subscription plan not found");
		}
		
		var shopSubscription = new ShopSubscription();
		shopSubscription.setTitle(subscription.getTitle());
		shopSubscription.setSubTotalPrice(subscription.getPrice());
		
		if (data.getPromoCodeId() != null) {
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
			
			if (promo.getMinConstraint() != null && promo.getMinConstraint().compareTo(subscription.getPrice()) == 1) {
				throw new ApplicationException(String.format("Promo code must used on %f and above", promo.getMinConstraint()));
			}
			
			if (promo.getValueType() == ValueType.FIXED_AMOUNT) {
				shopSubscription.setDiscount(promo.getValue());
			} else {
				var discount = subscription.getPrice().multiply(promo.getValue()).divide(BigDecimal.valueOf(100));
				shopSubscription.setDiscount(discount);
			}
			
			shopSubscription.setPromoCode(promo.getCode());
			
			subscriptionPromoDao.updateUsed(promo.getId(), true);
		
		}

		shopSubscription.setTotalPrice(shopSubscription.getSubTotalPrice().subtract(shopSubscription.getDiscount()));
		shopSubscription.setDuration(subscription.getDuration());
		shopSubscription.setShopId(shopId);
		
		var retry = false;
		var retryCount = 0;
		
		do {
			if (retryCount > 2) {
				throw new ApplicationException("Something went wrong. Please try again");
			}
			
			var latestSubscription = shopSubscriptionDao.findLatestSubscriptionByShop(shopId);
			shopSubscription.setPreSubscription(latestSubscription != null);
			
			if (shopSubscription.getTotalPrice().doubleValue() <= 0) {
				shopSubscription.setStatus(Status.SUCCESS);
				
				var duration = subscription.getDuration();
				
				if (latestSubscription == null) {
					var start = LocalDate.now();

					shopSubscription.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
					shopSubscription.setEndAt(start.plusDays(duration - 1).atTime(LocalTime.MAX)
							.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
				} else {
					var start = Instant.ofEpochMilli(latestSubscription.getEndAt()).atZone(ZoneOffset.UTC).toLocalDate()
							.plusDays(1);

					shopSubscription.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
					shopSubscription.setEndAt(start.plusDays(duration - 1).atTime(LocalTime.MAX)
							.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());

				}
				
				// Retry if conflict
				retry = shopSubscriptionDao.existsByShopIdAndStatusAndStartAt(shopId, ShopSubscription.Status.SUCCESS, shopSubscription.getStartAt());
				
				if (!retry) {
					shopDao.updateExpiredAt(shop.getId(), shopSubscription.getEndAt());
				}
				
			}
			
			if (retry) {
				retryCount += 1;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
		} while (retry);

		var shopSubscriptionId = shopSubscriptionDao.save(shopSubscription);

		if (shopSubscription.getTotalPrice().compareTo(BigDecimal.ZERO) == 1) {

			var paymentRequest = new PaymentTokenRequest();
			paymentRequest.setAmount(shopSubscription.getTotalPrice());
			paymentRequest.setInvoiceNo(String.valueOf(shopSubscriptionId));
			paymentRequest.setDescription(
					String.format("%s %d days subscription", subscription.getTitle(), subscription.getDuration()));

			var result = paymentGatewayAdapter.requestPaymentToken(paymentRequest);

			if (!"0000".equals(result.getRespCode())) {
				throw new ApplicationException("Something went wrong. Please try again");
			}

			return result;
		}

		return null;
	}

}
