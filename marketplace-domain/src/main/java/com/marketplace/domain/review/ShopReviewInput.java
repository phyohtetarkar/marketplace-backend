package com.marketplace.domain.review;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewInput {

	private long shopId;

    private long userId;

    private BigDecimal rating;

    private String description;
    
}
