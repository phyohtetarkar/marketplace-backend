package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.subscription.ShopSubscription;

public class GetCurrentSubscriptionByShopUseCase {

	private ShopSubscriptionDao shopSubscriptionDao;

	public GetCurrentSubscriptionByShopUseCase(ShopSubscriptionDao shopSubscriptionDao) {
		super();
		this.shopSubscriptionDao = shopSubscriptionDao;
	}

	public ShopSubscription apply(long shopId) {
		return shopSubscriptionDao.findCurrentSubscriptionByShop(shopId);
	}

}
