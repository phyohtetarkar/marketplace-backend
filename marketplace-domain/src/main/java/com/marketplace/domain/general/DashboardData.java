package com.marketplace.domain.general;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.domain.subscription.ShopSubscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardData {

	private BigDecimal totalSubscription;
	
	private long totalShop;
	
	private long totalProduct;
	
	private long totalUser;
	
	List<ShopSubscription> recentSubscriptions;
	
}
