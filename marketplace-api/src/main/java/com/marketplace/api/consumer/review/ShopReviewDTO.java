package com.marketplace.api.consumer.review;

import java.math.BigDecimal;

import com.marketplace.api.consumer.user.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewDTO {

    private BigDecimal rating;

    private String description;

    private UserDTO reviewer;

    private long createdAt;
}
