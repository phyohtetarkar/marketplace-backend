package com.shoppingcenter.app.controller.discount.dto;

import com.shoppingcenter.data.discount.DiscountEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountEditDTO {
    private long shopId;

    private String title;

    private double value;

    private DiscountEntity.Type type;
}
