package com.shoppingcenter.domain.shop.usecase;

import java.util.List;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.misc.City;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopDeliveryCityDao;

import lombok.Setter;

@Setter
public class SaveShopDeliveryCityUseCase {

	private ShopDeliveryCityDao shopDeliveryCityDao;
	
	private ShopDao shopDao;
	
	public void apply(long shopId, List<City> cities) {
		if (!shopDao.existsById(shopId)) {
			throw new ApplicationException("Shop not found");
		}
		
		shopDeliveryCityDao.deleteByShop(shopId);
		
		shopDeliveryCityDao.saveAll(shopId, cities);
	}
	
}
