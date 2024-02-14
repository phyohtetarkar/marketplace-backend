package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.ShopSettingInput;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shop.dao.ShopSettingDao;

@Component
public class SaveShopSettingUseCase {

	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ShopSettingDao shopSettingDao;
	
	public void apply(ShopSettingInput values) {
		if (!shopDao.existsById(values.getShopId())) {
			throw new ApplicationException("Shop not found");
		}
		
		shopSettingDao.save(values);
	}
	
}
