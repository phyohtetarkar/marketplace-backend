package com.shoppingcenter.app.controller.review.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewEditDTO {

    private long shopId;

    @JsonIgnore
    private long userId;

    private double rating;

    private String description;

}
