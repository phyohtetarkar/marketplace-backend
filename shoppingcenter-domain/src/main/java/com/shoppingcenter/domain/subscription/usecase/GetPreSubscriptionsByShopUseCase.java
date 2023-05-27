package com.shoppingcenter.domain.subscription.usecase;

import java.util.List;

import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.subscription.ShopSubscription;

public class GetPreSubscriptionsByShopUseCase {

	private ShopSubscriptionDao shopSubscriptionDao;

	public GetPreSubscriptionsByShopUseCase(ShopSubscriptionDao shopSubscriptionDao) {
		super();
		this.shopSubscriptionDao = shopSubscriptionDao;
	}

	public List<ShopSubscription> apply(long shopId) {
		return shopSubscriptionDao.findShopPreSubscriptions(shopId);
	}

}
