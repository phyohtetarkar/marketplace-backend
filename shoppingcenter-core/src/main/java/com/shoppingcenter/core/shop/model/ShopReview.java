package com.shoppingcenter.core.shop.model;

import com.shoppingcenter.core.user.model.User;
import com.shoppingcenter.data.shop.ShopReviewEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReview {

    private long shopId;

    private String userId;

    private double rating;

    private String description;

    private User reviewer;

    private long createdAt;

    public static ShopReview create(ShopReviewEntity entity, String baseUrl) {
        ShopReview review = new ShopReview();
        review.setShopId(entity.getId().getShopId());
        review.setUserId(entity.getId().getUserId());
        review.setRating(entity.getRating());
        review.setDescription(entity.getDescription());
        review.setReviewer(User.create(entity.getUser(), baseUrl));
        review.setCreatedAt(entity.getCreatedAt());
        return review;
    }
}
