package com.shoppingcenter.domain.sale.usecase;

import java.util.HashMap;
import java.util.List;

import com.shoppingcenter.domain.sale.SaleHistory;
import com.shoppingcenter.domain.sale.SaleHistoryDao;

public class GetMonthlySaleByShopUseCase {

	private SaleHistoryDao dao;

	public GetMonthlySaleByShopUseCase(SaleHistoryDao dao) {
		super();
		this.dao = dao;
	}

	public List<SaleHistory> apply(long shopId, int year) {
		var list = dao.findByShopAndYear(shopId, year);
		var map = new HashMap<Integer, SaleHistory>();
		for (int i = 1; i <= 12; i++) {
			var h = new SaleHistory();
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
