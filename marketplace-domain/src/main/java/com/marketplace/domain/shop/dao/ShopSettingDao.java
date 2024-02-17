package com.marketplace.domain.shop.dao;

import com.marketplace.domain.shop.ShopSetting;
import com.marketplace.domain.shop.ShopSettingInput;

public interface ShopSettingDao {

	void save(ShopSettingInput values);
	
	ShopSetting findByShop(long shopId);
	
}
