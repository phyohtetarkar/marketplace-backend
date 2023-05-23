package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;

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
