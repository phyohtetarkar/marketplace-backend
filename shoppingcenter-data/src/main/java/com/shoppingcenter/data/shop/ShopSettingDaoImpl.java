package com.shoppingcenter.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.shop.ShopSetting;
import com.shoppingcenter.domain.shop.dao.ShopSettingDao;

@Repository
public class ShopSettingDaoImpl implements ShopSettingDao {
	
	@Autowired
	private ShopRepo shopRepo;
	
	@Autowired
	private ShopSettingRepo shopSettingRepo;

	@Override
	public void save(ShopSetting setting) {
		var entity = shopSettingRepo.findById(setting.getShopId()).orElseGet(ShopSettingEntity::new);
		entity.setBankTransfer(setting.isBankTransfer());
		entity.setCashOnDelivery(setting.isCashOnDelivery());
		entity.setShop(shopRepo.getReferenceById(setting.getShopId()));
		
		shopSettingRepo.save(entity);
	}

	@Override
	public ShopSetting findByShop(long shopId) {
		return shopSettingRepo.findById(shopId).map(e -> {
			var setting = new ShopSetting();
			setting.setShopId(shopId);
			setting.setCashOnDelivery(e.isCashOnDelivery());
			setting.setBankTransfer(e.isBankTransfer());
			return setting;
		}).orElse(null);
	}

}
