package com.shoppingcenter.domain.shop.usecase;

import java.util.List;

import com.shoppingcenter.domain.misc.City;
import com.shoppingcenter.domain.shop.dao.ShopDeliveryCityDao;

public class GetAllShopDeliveryCityUseCase {

	private ShopDeliveryCityDao dao;

	public GetAllShopDeliveryCityUseCase(ShopDeliveryCityDao dao) {
		super();
		this.dao = dao;
	}

	public List<City> apply(long shopId) {
		return dao.findByShop(shopId);
	}
}
