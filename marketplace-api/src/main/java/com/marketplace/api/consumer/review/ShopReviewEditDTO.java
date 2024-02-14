package com.marketplace.api.consumer.review;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewEditDTO {

	@JsonIgnore
    private long shopId;

    @JsonIgnore
    private long userId;

    private BigDecimal rating;

    private String description;

}
