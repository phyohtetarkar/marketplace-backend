package com.shoppingcenter.domain.sale;

import java.time.YearMonth;
import java.util.List;

public interface SaleHistoryDao {
	
	void save(SaleHistory history);
	
	double getTotalSaleByShop(long shopId);
	
	SaleHistory findByShopAndYearMonth(long shopId, YearMonth ym);

	List<SaleHistory> findByShopAndYear(long shopId, int year);
	
}
