package com.shoppingcenter.data.sale;

import java.time.YearMonth;
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
	public void save(SaleHistory history) {
		var id = new SaleHistoryEntity.ID(history.getShopId(), history.getYear(), history.getMonth());
		var entity = saleHistoryRepo.findById(id).orElseGet(SaleHistoryEntity::new);
		entity.setId(id);
		
		entity.setTotalSale(history.getTotalSale());
		
		saleHistoryRepo.save(entity);
	}
	
	@Override
	public double getTotalSaleByShop(long shopId) {
		return saleHistoryRepo.totalSaleByShop(shopId);
	}
	
	@Override
	public SaleHistory findByShopAndYearMonth(long shopId, YearMonth ym) {
		var id = new SaleHistoryEntity.ID(shopId, ym);
		return saleHistoryRepo.findById(id).map(SaleHistoryMapper::toDomain).orElse(null);
	}

	@Override
	public List<SaleHistory> findByShopAndYear(long shopId, int year) {
		return saleHistoryRepo.findById_ShopIdAndId_Year(shopId, year).stream()
				.map(SaleHistoryMapper::toDomain).toList();
	}

}
