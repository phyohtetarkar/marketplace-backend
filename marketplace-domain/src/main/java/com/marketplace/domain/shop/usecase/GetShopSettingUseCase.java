package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shop.ShopSetting;
import com.marketplace.domain.shop.dao.ShopSettingDao;

@Component
public class GetShopSettingUseCase {

	@Autowired
	private ShopSettingDao dao;

	public ShopSetting apply(long shopId) {
		return dao.findByShop(shopId);
	}

}
