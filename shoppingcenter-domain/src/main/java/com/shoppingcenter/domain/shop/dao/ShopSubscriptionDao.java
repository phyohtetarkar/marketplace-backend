package com.shoppingcenter.domain.shop.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.ShopSubscriptionQuery;

public interface ShopSubscriptionDao {
	
	long save(ShopSubscription subscription);
	
	void deleteById(long id);
	
	ShopSubscription findById(long id);
	
	ShopSubscription findCurrentSubscriptionByShop(long shopId);
	
	ShopSubscription findLatestSubscriptionByShop(long shopId);
	
	List<ShopSubscription> findShopPreSubscriptions(long shopId);
	
	PageData<ShopSubscription> findAll(ShopSubscriptionQuery query);
	
}
