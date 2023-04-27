package com.shoppingcenter.data.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.sale.SaleHistory;
import com.shoppingcenter.domain.sale.SaleHistoryDao;

@Repository
public class SaleHistoryDaoImpl implements SaleHistoryDao {
	
	@Autowired
	private SaleHistoryRepo saleHistoryRepo;

	@Override
	public double getTotalSaleByShop(long shopId) {
		return saleHistoryRepo.totalSaleByShop(shopId);
	}

	@Override
	public List<SaleHistory> findByShopAndYear(long shopId, int year) {
		return saleHistoryRepo.findByShopIdAndYear(shopId, year).stream()
				.map(e -> {
					var history = new SaleHistory();
					history.setYear(e.getId().getYear());
					history.setMonth(e.getId().getMonth());
					history.setTotalSale(e.getTotalSale());
					return history;
				}).toList();
	}

}
