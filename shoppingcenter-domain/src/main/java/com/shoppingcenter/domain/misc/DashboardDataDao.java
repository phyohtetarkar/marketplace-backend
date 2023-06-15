package com.shoppingcenter.domain.misc;

import java.util.List;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.subscription.ShopSubscription;

public interface DashboardDataDao {

	Statistic getStatistic();
	
	List<ShopSubscription> findLatest10SuccessfulSubscription();
	
	List<Shop> findLatest10PendingShop();
}
