package com.marketplace.data.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopReviewRepo extends JpaRepository<ShopReviewEntity, ShopReviewEntity.ID> {

	Page<ShopReviewEntity> findByShopId(long shopId, Pageable pageable);

	long countByShopIdAndRating(long shopId, double rating);

	void deleteByShopId(long shopId);

//	@Query(value = "SELECT COALESCE(AVG(sr.rating), 0.0) FROM ShopReview sr WHERE sr.id.shopId = ?1")
//	double averageRatingByShop(long shopId);

}
