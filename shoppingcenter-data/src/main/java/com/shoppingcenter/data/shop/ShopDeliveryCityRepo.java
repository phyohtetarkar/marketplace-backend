package com.shoppingcenter.data.shop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopDeliveryCityRepo extends JpaRepository<ShopDeliveryCityEntity, ShopDeliveryCityEntity.ID> {

	List<ShopDeliveryCityEntity> findByShopId(long shopId);
	
	void deleteByShopId(long shopId);
	
}
