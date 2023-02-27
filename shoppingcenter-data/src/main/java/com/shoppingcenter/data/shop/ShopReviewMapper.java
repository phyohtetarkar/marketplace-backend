package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.user.UserMapper;
import com.shoppingcenter.domain.shop.ShopReview;

public class ShopReviewMapper {

    public static ShopReview toDomain(ShopReviewEntity entity, String baseUrl) {
        var review = new ShopReview();
        review.setId(entity.getId());
        review.setUserId(entity.getUser().getId());
        review.setRating(entity.getRating());
        review.setDescription(entity.getDescription());
        review.setReviewer(UserMapper.toDomain(entity.getUser(), baseUrl));
        review.setUpdatedAt(entity.getModifiedAt());
        return review;
    }

}
