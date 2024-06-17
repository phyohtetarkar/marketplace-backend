package com.marketplace.data.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRatingRepo extends JpaRepository<ShopRatingEntity, Long> {

	@Modifying
	@Query("UPDATE ShopRating r SET r.count = r.count + 1"
			+ ", r.rating = (SELECT AVG(sr.rating) FROM ShopReview sr WHERE sr.id.shopId = :id) "
			+ "WHERE r.id = :id")
	void updateRatingAndCount(@Param("id") long id);
	
	@Modifying
	@Query("UPDATE ShopRating r SET "
			+ "r.rating = (SELECT AVG(sr.rating) FROM ShopReview sr WHERE sr.id.shopId = :id) "
			+ "WHERE r.id = :id")
	void updateRating(@Param("id") long id);

}
