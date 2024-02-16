package com.marketplace.domain.subscription.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.subscription.ShopSubscription;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDao;

@Component
public class GetActiveSubscriptionsByShopUseCase {

	@Autowired
	private ShopSubscriptionDao shopSubscriptionDao;

	@Transactional(readOnly = true)
	public List<ShopSubscription> apply(long shopId) {
		var result = new ArrayList<ShopSubscription>();
		var currentTime = System.currentTimeMillis();
		var currentSubscription = shopSubscriptionDao.findShopSubscriptionByShopAndTime(shopId, currentTime);

		if (currentSubscription != null) {
			result.add(currentSubscription);
			var preSubscriptions = shopSubscriptionDao.findShopPreSubscriptions(shopId, currentSubscription.getEndAt());

			result.addAll(preSubscriptions);

			result.sort((f, s) -> Long.compare(s.getStartAt(), f.getStartAt()));
		}

		return result;
	}

}
