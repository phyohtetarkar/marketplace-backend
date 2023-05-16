package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.user.UserMapper;
import com.shoppingcenter.domain.shop.ShopReview;

public class ShopReviewMapper {

    public static ShopReview toDomain(ShopReviewEntity entity) {
        var review = new ShopReview();
        review.setUserId(entity.getId().getUserId());
        review.setShopId(entity.getId().getShopId());
        review.setRating(entity.getRating());
        review.setDescription(entity.getDescription());
        review.setReviewer(UserMapper.toDomain(entity.getUser()));
        review.setCreatedAt(entity.getCreatedAt());
        return review;
    }

}
