package com.shoppingcenter.core.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.core.user.model.User;
import com.shoppingcenter.data.shop.ShopReviewEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReview {

    private long shopId;

    @JsonIgnore
    private String userId;

    private double rating;

    private String description;

    @JsonProperty(access = Access.READ_ONLY)
    private User reviewer;

    public static ShopReview create(ShopReviewEntity entity, String baseUrl) {
        ShopReview review = new ShopReview();
        review.setShopId(entity.getId().getShopId());
        review.setUserId(entity.getId().getUserId());
        review.setRating(entity.getRating());
        review.setDescription(entity.getDescription());
        review.setReviewer(User.create(entity.getUser(), baseUrl));
        return review;
    }
}
