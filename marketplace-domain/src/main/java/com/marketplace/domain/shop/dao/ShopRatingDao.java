package com.marketplace.domain.shop.dao;

import com.marketplace.domain.shop.ShopRating;

public interface ShopRatingDao {

	void save(ShopRating values);
	
	ShopRating findByShop(long shopId);
}
