package com.marketplace.domain.shop.usecase;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shop.ShopMonthlySale;
import com.marketplace.domain.shop.dao.ShopMonthlySaleDao;

@Component
public class GetMonthlySaleByShopUseCase {

	@Autowired
	private ShopMonthlySaleDao dao;

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
