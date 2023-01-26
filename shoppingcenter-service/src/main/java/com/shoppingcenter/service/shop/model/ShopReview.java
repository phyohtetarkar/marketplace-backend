package com.shoppingcenter.service.shop.model;

import com.shoppingcenter.data.shop.ShopReviewEntity;
import com.shoppingcenter.service.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReview {

    private long id;

    private long shopId;

    private String userId;

    private double rating;

    private String description;

    private User reviewer;

    private long createdAt;

    public static ShopReview create(ShopReviewEntity entity, String baseUrl) {
        ShopReview review = new ShopReview();
        review.setId(entity.getId());
        review.setUserId(entity.getUser().getId());
        review.setRating(entity.getRating());
        review.setDescription(entity.getDescription());
        review.setReviewer(User.create(entity.getUser(), baseUrl));
        review.setCreatedAt(entity.getCreatedAt());
        return review;
    }
}
