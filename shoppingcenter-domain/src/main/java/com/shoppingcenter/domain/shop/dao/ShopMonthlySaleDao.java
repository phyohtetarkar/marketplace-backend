package com.shoppingcenter.domain.shop.dao;

import java.time.YearMonth;
import java.util.List;

import com.shoppingcenter.domain.shop.ShopMonthlySale;

public interface ShopMonthlySaleDao {

	void save(ShopMonthlySale sale);
	
	double getTotalSaleByShop(long shopId);
	
	ShopMonthlySale findByShopAndYearMonth(long shopId, YearMonth ym);

	List<ShopMonthlySale> findByShopAndYear(long shopId, int year);
	
}
