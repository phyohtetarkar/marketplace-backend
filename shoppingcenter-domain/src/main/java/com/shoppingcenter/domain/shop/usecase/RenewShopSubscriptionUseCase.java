package com.shoppingcenter.domain.shop.usecase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.payment.PaymentGatewayAdapter;
import com.shoppingcenter.domain.payment.PaymentTokenRequest;
import com.shoppingcenter.domain.payment.PaymentTokenResponse;
import com.shoppingcenter.domain.shop.RenewShopSubscriptionInput;
import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.ShopSubscription.Status;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;

import lombok.Setter;

@Setter
public class RenewShopSubscriptionUseCase {

	private ShopDao shopDao;

	private ShopMemberDao shopMemberDao;

	private ShopSubscriptionDao shopSubscriptionDao;

	private SubscriptionPlanDao subscriptionPlanDao;

	private PaymentGatewayAdapter paymentGatewayAdapter;

	public PaymentTokenResponse apply(RenewShopSubscriptionInput data) {
		
		var shop = shopDao.findById(data.getShopId());

		if (shop == null) {
			throw new ApplicationException("Shop not found");
		}

		var member = shopMemberDao.findByShopAndUser(data.getShopId(), data.getUserId());

		if (member == null) {
			throw new ApplicationException("Shop not found");
		}

		var subscription = subscriptionPlanDao.findById(data.getSubscriptionPlanId());

		if (subscription == null) {
			throw new ApplicationException("Subscription plan not found");
		}

		var latestSubscription = shopSubscriptionDao.findLatestSubscriptionByShop(data.getShopId());

		var shopSubscription = new ShopSubscription();
		shopSubscription.setTitle(subscription.getTitle());
		shopSubscription.setSubTotalPrice(subscription.getPrice());
		// TODO: check promo

		shopSubscription.setTotalPrice(shopSubscription.getSubTotalPrice().subtract(shopSubscription.getDiscount()));
		shopSubscription.setDuration(subscription.getDuration());
		shopSubscription.setShopId(data.getShopId());

		if (latestSubscription == null) {
			var start = LocalDate.now();

			shopSubscription.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
			shopSubscription.setEndAt(start.plusDays(subscription.getDuration() - 1).atTime(LocalTime.MAX)
					.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
		} else {
			var start = Instant.ofEpochMilli(latestSubscription.getEndAt()).atZone(ZoneOffset.UTC).toLocalDate()
					.plusDays(1);

			shopSubscription.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
			shopSubscription.setEndAt(start.plusDays(subscription.getDuration() - 1).atTime(LocalTime.MAX)
					.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());

			shopSubscription.setPreSubscription(true);
		}
		
		if (shopSubscription.getTotalPrice().doubleValue() <= 0) {
			shopSubscription.setStatus(Status.SUCCESS);
			shopDao.updateExpiredAt(shop.getId(), shopSubscription.getEndAt());
		}

		var shopSubscriptionId = shopSubscriptionDao.save(shopSubscription);

		if (shopSubscription.getTotalPrice().doubleValue() > 0) {

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
