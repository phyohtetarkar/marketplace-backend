package com.marketplace.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.shop.ShopSetting;
import com.marketplace.domain.shop.ShopSettingInput;
import com.marketplace.domain.shop.dao.ShopSettingDao;

@Repository
public class ShopSettingDaoImpl implements ShopSettingDao {
	
	@Autowired
	private ShopRepo shopRepo;
	
	@Autowired
	private ShopSettingRepo shopSettingRepo;

	@Override
	public void save(ShopSettingInput values) {
		var entity = shopSettingRepo.findById(values.getShopId()).orElseGet(ShopSettingEntity::new);
		entity.setBankTransfer(values.isBankTransfer());
		entity.setCashOnDelivery(values.isCashOnDelivery());
		entity.setOrderNote(values.getOrderNote());
		entity.setShop(shopRepo.getReferenceById(values.getShopId()));
		
		shopSettingRepo.save(entity);
	}

	@Override
	public ShopSetting findByShop(long shopId) {
		return shopSettingRepo.findById(shopId).map(e -> {
			var setting = new ShopSetting();
			setting.setCashOnDelivery(e.isCashOnDelivery());
			setting.setBankTransfer(e.isBankTransfer());
			setting.setOrderNote(e.getOrderNote());
			return setting;
		}).orElse(null);
	}

}
