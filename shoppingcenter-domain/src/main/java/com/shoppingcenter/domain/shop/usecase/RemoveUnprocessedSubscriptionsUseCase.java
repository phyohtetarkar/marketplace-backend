package com.shoppingcenter.domain.shop.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;

public class RemoveUnprocessedSubscriptionsUseCase {

	private ShopSubscriptionDao shopSubscriptionDao;

	public RemoveUnprocessedSubscriptionsUseCase(ShopSubscriptionDao shopSubscriptionDao) {
		super();
		this.shopSubscriptionDao = shopSubscriptionDao;
	}

	public void apply() {
		var timeDiff = Instant.now().minus(1, ChronoUnit.DAYS).toEpochMilli();
		var status = ShopSubscription.Status.PROCESSING;

		shopSubscriptionDao.deleteByStatusCreatedAtLessThan(status, timeDiff);

	}

}
