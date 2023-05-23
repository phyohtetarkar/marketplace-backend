package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopSetting;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopSettingDao;

import lombok.Setter;

@Setter
public class SaveShopSettingUseCase {

	private ShopDao shopDao;
	
	private ShopSettingDao shopSettingDao;
	
	public void apply(ShopSetting setting) {
		if (!shopDao.existsById(setting.getShopId())) {
			throw new ApplicationException("Shop not found");
		}
		
		// TODO : need to discuss
		setting.setCashOnDelivery(true);
		
		shopSettingDao.save(setting);
	}
	
}
