package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.subscription.ShopSubscription;
import com.shoppingcenter.domain.subscription.ShopSubscriptionQuery;

public class GetAllShopSubscriptionUseCase {

	private ShopSubscriptionDao shopSubscriptionDao;

	public GetAllShopSubscriptionUseCase(ShopSubscriptionDao shopSubscriptionDao) {
		super();
		this.shopSubscriptionDao = shopSubscriptionDao;
	}

	public PageData<ShopSubscription> apply(ShopSubscriptionQuery query) {
		return shopSubscriptionDao.findAll(query);
	}

}
