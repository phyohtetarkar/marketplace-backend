package com.marketplace.data.shop;

import com.marketplace.domain.shop.ShopMonthlySale;

public interface ShopMonthlySaleMapper {

	static ShopMonthlySale toDomain(ShopMonthlySaleEntity entity) {
		var history = new ShopMonthlySale();
		history.setYear(entity.getId().getYear());
		history.setMonth(entity.getId().getMonth());
		history.setTotalSale(entity.getTotalSale());
		return history;
	}
	
}
