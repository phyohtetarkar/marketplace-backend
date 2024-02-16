package com.marketplace.data.review;

import com.marketplace.data.user.UserMapper;
import com.marketplace.domain.review.ShopReview;

public interface ShopReviewMapper {

    public static ShopReview toDomain(ShopReviewEntity entity) {
        var review = new ShopReview();
        review.setRating(entity.getRating());
        review.setDescription(entity.getDescription());
        review.setReviewer(UserMapper.toDomain(entity.getUser()));
        review.setCreatedAt(entity.getCreatedAt());
        return review;
    }

}
