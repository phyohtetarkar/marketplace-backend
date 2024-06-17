package com.marketplace.domain.shop.dao;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import com.marketplace.domain.shop.ShopMonthlySale;

public interface ShopMonthlySaleDao {

	void save(long shopId, ShopMonthlySale sale);
	
	void updateTotalSale(long shopId, YearMonth yearMonth, BigDecimal value);
	
	double getTotalSaleByShop(long shopId);
	
	ShopMonthlySale findByShopAndYearMonth(long shopId, YearMonth ym);

	List<ShopMonthlySale> findByShopAndYear(long shopId, int year);
	
}
