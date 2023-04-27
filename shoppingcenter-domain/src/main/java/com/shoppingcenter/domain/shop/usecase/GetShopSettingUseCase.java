package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.ShopSetting;
import com.shoppingcenter.domain.shop.dao.ShopSettingDao;

public class GetShopSettingUseCase {

	private ShopSettingDao dao;

	public GetShopSettingUseCase(ShopSettingDao dao) {
		super();
		this.dao = dao;
	}

	public ShopSetting apply(long shopId) {
		return dao.findByShop(shopId);
	}

}
