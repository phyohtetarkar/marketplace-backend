package com.shoppingcenter.domain.shop.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.ShopSubscriptionQuery;

public interface ShopSubscriptionDao {
	
	long save(ShopSubscription subscription);
	
	void deleteById(long id);
	
	ShopSubscription findById(long id);
	
	ShopSubscription findCurrentActiveByShop(long shopId);
	
	List<ShopSubscription> findShopSubscriptions(long shopId, long startAt);
	
	PageData<ShopSubscription> findAll(ShopSubscriptionQuery query);
	
}
