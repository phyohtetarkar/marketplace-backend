package com.shoppingcenter.domain.sale;

import java.util.List;

public interface SaleHistoryDao {
	
	double getTotalSaleByShop(long shopId);

	List<SaleHistory> findByShopAndYear(long shopId, int year);
	
}
