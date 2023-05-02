package com.shoppingcenter.app.controller.discount.dto;

import java.math.BigDecimal;

import com.shoppingcenter.domain.discount.Discount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountEditDTO {
    private long id;

    private long shopId;

    private String title;

    private BigDecimal value;

    private Discount.Type type;
}
