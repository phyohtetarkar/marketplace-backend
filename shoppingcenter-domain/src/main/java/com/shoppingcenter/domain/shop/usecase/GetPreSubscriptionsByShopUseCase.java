package com.shoppingcenter.domain.shop.usecase;

import java.util.List;

import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;

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
