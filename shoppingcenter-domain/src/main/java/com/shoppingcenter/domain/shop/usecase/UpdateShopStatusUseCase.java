package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class UpdateShopStatusUseCase {

	private ShopDao shopDao;
	
	public void apply(long shopId, Shop.Status status) {
		if (!shopDao.existsById(shopId)) {
			throw new ApplicationException("Shop not found");
		}
		shopDao.updateStatus(shopId, status);
	}
	
}
