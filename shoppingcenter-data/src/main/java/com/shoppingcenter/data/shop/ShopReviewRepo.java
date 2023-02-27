package com.shoppingcenter.data.shop;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopReviewRepo extends JpaRepository<ShopReviewEntity, Long> {

	Optional<ShopReviewEntity> findByShop_IdAndUser_Id(long shopId, String userId);

	Page<ShopReviewEntity> findByShop_Id(long shopId, Pageable pageable);

	long countByShop_IdAndRating(long shopId, double rating);

	void deleteByShop_Id(long shopId);

	void deleteByIdAndUser_Id(long id, String userId);

	boolean existsByShop_IdAndUser_Id(long shopId, String userId);

	boolean existsByIdAndUser_Id(long id, String userId);

	@Query(value = "SELECT COALESCE(AVG(sr.rating), 0.0) FROM ShopReview sr WHERE sr.shop.id = ?1")
	double averageRatingByShop(long shopId);

}
