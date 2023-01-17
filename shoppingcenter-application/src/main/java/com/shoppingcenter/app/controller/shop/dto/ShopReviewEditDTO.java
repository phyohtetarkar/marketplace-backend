package com.shoppingcenter.app.controller.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewEditDTO {
    private String id;

    private long shopId;

    private String userId;

    private double rating;

    private String description;

}
