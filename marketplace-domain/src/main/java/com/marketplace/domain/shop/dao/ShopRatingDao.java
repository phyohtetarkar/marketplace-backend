package com.marketplace.domain.shop.dao;

import com.marketplace.domain.shop.ShopRating;

public interface ShopRatingDao {

	void save(ShopRating values);
	
	void updateRatingAndCount(long shopId);
	
	void updateRating(long shopId);
	
	ShopRating findByShop(long shopId);
}
