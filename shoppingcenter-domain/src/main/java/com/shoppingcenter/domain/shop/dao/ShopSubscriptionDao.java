package com.shoppingcenter.domain.shop.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.ShopSubscriptionQuery;

public interface ShopSubscriptionDao {
	
	long save(ShopSubscription subscription);
	
	void deleteById(long id);
	
	void deleteByStatusCreatedAtLessThan(ShopSubscription.Status status, long createdAt);
	
	boolean existsByShopIdAndStatusAndStartAt(long shopId, ShopSubscription.Status status, long startAt);
	
	ShopSubscription findById(long id);
	
	ShopSubscription findCurrentSubscriptionByShop(long shopId);
	
	ShopSubscription findLatestSubscriptionByShop(long shopId);
	
	List<ShopSubscription> findShopPreSubscriptions(long shopId);
	
	PageData<ShopSubscription> findAll(ShopSubscriptionQuery query);
	
}
