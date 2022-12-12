package com.shoppingcenter.data.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopReviewRepo extends JpaRepository<ShopReviewEntity, ShopReviewEntity.Id> {

	Page<ShopReviewEntity> findByShopId(long shopId, Pageable pageable);
	
	long countByShopIdAndRating(long shopId, double rating);
	
	void deleteByShopId(long shopId);
	
}
