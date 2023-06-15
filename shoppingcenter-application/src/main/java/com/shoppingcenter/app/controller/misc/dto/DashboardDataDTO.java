package com.shoppingcenter.app.controller.misc.dto;

import java.util.List;

import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopSubscriptionDTO;
import com.shoppingcenter.domain.misc.Statistic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardDataDTO {

	private Statistic statistic;
	
	private List<ShopSubscriptionDTO> recentSubscriptions;
	
	private List<ShopDTO> pendingShops;
	
}
