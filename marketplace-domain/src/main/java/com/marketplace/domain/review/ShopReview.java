package com.marketplace.domain.review;

import java.math.BigDecimal;

import com.marketplace.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReview {

    private BigDecimal rating;

    private String description;

    private User reviewer;
    
    private long createdAt;

}
