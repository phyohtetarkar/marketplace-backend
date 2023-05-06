package com.shoppingcenter.data.sale;

import com.shoppingcenter.domain.sale.SaleHistory;

public class SaleHistoryMapper {

	public static SaleHistory toDomain(SaleHistoryEntity entity) {
		var history = new SaleHistory();
		history.setShopId(entity.getId().getShopId());
		history.setYear(entity.getId().getYear());
		history.setMonth(entity.getId().getMonth());
		history.setTotalSale(entity.getTotalSale());
		return history;
	}
	
}
