package com.marketplace.domain.subscription.dao;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.subscription.ShopSubscription;
import com.marketplace.domain.subscription.ShopSubscriptionTime;

public interface ShopSubscriptionDao {
	
	ShopSubscription save(ShopSubscription subscription);
	
	void saveTime(ShopSubscriptionTime time);
	
	boolean existsByShopIdAndStatusAndStartAt(long shopId, ShopSubscription.Status status, long startAt);
	
	BigDecimal getTotalSubscriptionPrice();
	
	ShopSubscription findByInvoiceNo(long invoiceNo);
	
	ShopSubscription findShopSubscriptionByShopAndTime(long shopId, long time);
	
	ShopSubscription findLatestSubscriptionByShop(long shopId);
	
	List<ShopSubscription> findShopPreSubscriptions(long shopId, long startAt);
	
	List<ShopSubscription> findLatest10SuccessfulSubscription();
	
	PageData<ShopSubscription> findAll(SearchQuery searchQuery);
	
}
