package com.shoppingcenter.data.shop;

import com.shoppingcenter.domain.shop.ShopMonthlySale;

public class ShopMonthlySaleMapper {

	public static ShopMonthlySale toDomain(ShopMonthlySaleEntity entity) {
		var history = new ShopMonthlySale();
		history.setShopId(entity.getId().getShopId());
		history.setYear(entity.getId().getYear());
		history.setMonth(entity.getId().getMonth());
		history.setTotalSale(entity.getTotalSale());
		return history;
	}
	
}
