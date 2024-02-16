package com.marketplace.data.shop;

import com.marketplace.domain.shop.ShopRating;

public interface ShopRatingMapper {

	static ShopRating toDomain(ShopRatingEntity entity) {
		var rating = new ShopRating();
		rating.setShopId(entity.getId());
		rating.setRating(entity.getRating());
		rating.setCount(entity.getCount());
		return rating;
	}
	
}
