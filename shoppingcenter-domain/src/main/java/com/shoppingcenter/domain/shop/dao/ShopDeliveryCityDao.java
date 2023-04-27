package com.shoppingcenter.domain.shop.dao;

import java.util.List;

import com.shoppingcenter.domain.misc.City;

public interface ShopDeliveryCityDao {

	void saveAll(long shopId, List<City> cities);
	
	void deleteByShop(long shopId);
	
	boolean exists(long shopId, long cityId);
	
	List<City> findByShop(long shopId);
	
}
