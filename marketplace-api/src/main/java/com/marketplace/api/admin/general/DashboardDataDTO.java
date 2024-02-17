package com.marketplace.api.admin.general;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.api.admin.subscription.ShopSubscriptionDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardDataDTO {

	private BigDecimal totalSubscription;
	
	private long totalShop;
	
	private long totalProduct;
	
	private long totalUser;	
	
	private List<ShopSubscriptionDTO> recentSubscriptions;
	
}
