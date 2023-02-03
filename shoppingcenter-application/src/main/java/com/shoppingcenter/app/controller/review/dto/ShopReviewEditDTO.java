package com.shoppingcenter.app.controller.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewEditDTO {
    private long id;

    private long shopId;

    private String userId;

    private double rating;

    private String description;

}
