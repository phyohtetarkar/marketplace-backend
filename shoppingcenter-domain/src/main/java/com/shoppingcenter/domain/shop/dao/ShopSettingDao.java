package com.shoppingcenter.domain.shop.dao;

import com.shoppingcenter.domain.shop.ShopSetting;

public interface ShopSettingDao {

	void save(ShopSetting setting);
	
	ShopSetting findByShop(long shopId);
	
}
