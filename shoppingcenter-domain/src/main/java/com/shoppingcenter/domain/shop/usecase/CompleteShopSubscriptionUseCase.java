package com.shoppingcenter.domain.shop.usecase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import com.shoppingcenter.domain.payment.PaymentResult;
import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.ShopSubscription.Status;
import com.shoppingcenter.domain.shop.ShopSubscriptionTransaction;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionTransactionDao;

import lombok.Setter;

@Setter
public class CompleteShopSubscriptionUseCase {

	private ShopDao shopDao;

	private ShopSubscriptionDao shopSubscriptionDao;

	private ShopSubscriptionTransactionDao shopSubscriptionTransactionDao;

	public void apply(PaymentResult result) {
		if (result == null) {
			return;
		}
		var trans = new ShopSubscriptionTransaction(result);

		var shopSubscription = shopSubscriptionDao.findById(trans.getShopSubscriptionId());

		if (shopSubscription == null) {
			return;
		}

		var shopId = shopSubscription.getShopId();

		var retry = false;
		var retryCount = 0;

		do {
			if (retryCount > 4) {
				return;
			}

			if ("0000".equals(trans.getRespCode())) {
				shopSubscription.setStatus(ShopSubscription.Status.SUCCESS);
				
				var latestSubscription = shopSubscriptionDao.findLatestSubscriptionByShop(shopId);

				var duration = shopSubscription.getDuration();

				if (latestSubscription == null) {
					var start = LocalDate.now();

					shopSubscription.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
					shopSubscription.setEndAt(start.plusDays(duration - 1).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC)
							.toInstant().toEpochMilli());

					shopSubscription.setPreSubscription(false);
				} else {
					var start = Instant.ofEpochMilli(latestSubscription.getEndAt()).atZone(ZoneOffset.UTC).toLocalDate()
							.plusDays(1);

					shopSubscription.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
					shopSubscription.setEndAt(start.plusDays(duration - 1).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC)
							.toInstant().toEpochMilli());

					shopSubscription.setPreSubscription(true);
				}

				// Retry if conflict
				retry = shopSubscriptionDao.existsByShopIdAndStatusAndStartAt(shopId, ShopSubscription.Status.SUCCESS,
						shopSubscription.getStartAt());
			} else if ("0001".equals(trans.getRespCode()) || "2001".equals(trans.getRespCode())) {
				retry = false;
				shopSubscription.setStatus(ShopSubscription.Status.PROCESSING);
			} else {
				retry = false;
				shopSubscription.setStatus(ShopSubscription.Status.FAILED);
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

		shopSubscriptionDao.save(shopSubscription);

		shopSubscriptionTransactionDao.save(trans);

		var status = shopSubscription.getStatus();

		if (status == Status.SUCCESS) {
			shopDao.updateExpiredAt(shopId, shopSubscription.getEndAt());
		}

	}

}
