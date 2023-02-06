package com.shoppingcenter.app.controller.review.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewEditDTO {
    private long id;

    private long shopId;

    @JsonIgnore
    private String userId;

    private double rating;

    private String description;

}
