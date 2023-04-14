package com.shoppingcenter.data.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopReviewRepo extends JpaRepository<ShopReviewEntity, ShopReviewEntity.ID> {

	Page<ShopReviewEntity> findByShopId(long shopId, Pageable pageable);

	long countByShopIdAndRating(long shopId, double rating);

	void deleteByShopId(long shopId);

	@Query(value = "SELECT COALESCE(AVG(sr.rating), 0.0) FROM ShopReview sr WHERE sr.shop_id = ?1")
	double averageRatingByShop(long shopId);

}
