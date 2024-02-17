package com.marketplace.data.shop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopMonthlySaleRepo extends JpaRepository<ShopMonthlySaleEntity, ShopMonthlySaleEntity.ID> {

	List<ShopMonthlySaleEntity> findById_ShopIdAndId_Year(long shopId, int year);

	@Query(value = "SELECT COALESCE(SUM(s.totalSale), 0.0) FROM ShopMonthlySale s WHERE s.id.shopId = ?1")
	double totalSaleByShop(long shopId);
	
}
