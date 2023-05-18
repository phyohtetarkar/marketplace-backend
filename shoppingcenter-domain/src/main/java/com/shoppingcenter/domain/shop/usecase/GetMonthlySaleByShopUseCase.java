package com.shoppingcenter.domain.shop.usecase;

import java.util.HashMap;
import java.util.List;

import com.shoppingcenter.domain.shop.ShopMonthlySale;
import com.shoppingcenter.domain.shop.dao.ShopMonthlySaleDao;

public class GetMonthlySaleByShopUseCase {

	private ShopMonthlySaleDao dao;

	public GetMonthlySaleByShopUseCase(ShopMonthlySaleDao dao) {
		super();
		this.dao = dao;
	}

	public List<ShopMonthlySale> apply(long shopId, int year) {
		var list = dao.findByShopAndYear(shopId, year);
		var map = new HashMap<Integer, ShopMonthlySale>();
		for (int i = 1; i <= 12; i++) {
			var h = new ShopMonthlySale();
			h.setYear(year);
			h.setMonth(i);
			map.put(i, h);
		}
		
		for (var h : list) {
			map.put(h.getMonth(), h);
		}
		
		return map.values().stream().toList();
		
	}

}
