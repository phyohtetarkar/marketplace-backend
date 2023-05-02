package com.shoppingcenter.data.sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleHistoryRepo extends JpaRepository<SaleHistoryEntity, SaleHistoryEntity.ID> {
	
	List<SaleHistoryEntity> findById_ShopIdAndId_Year(long shopId, int year);

	@Query(value = "SELECT COALESCE(SUM(sh.totalSale), 0.0) FROM SaleHistory sh WHERE sh.id.shopId = ?1")
	double totalSaleByShop(long shopId);
	
}
