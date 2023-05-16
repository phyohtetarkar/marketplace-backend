package com.shoppingcenter.domain.shop;

import com.shoppingcenter.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReview {

    private long shopId;

    private long userId;

    private double rating;

    private String description;

    private User reviewer;

    private Long createdAt;

}
