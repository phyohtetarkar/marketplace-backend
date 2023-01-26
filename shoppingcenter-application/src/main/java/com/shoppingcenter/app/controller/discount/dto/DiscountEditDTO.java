package com.shoppingcenter.app.controller.discount.dto;

import com.shoppingcenter.service.discount.model.Discount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountEditDTO {
    private long id;

    private long shopId;

    private String title;

    private double value;

    private Discount.Type type;
}
