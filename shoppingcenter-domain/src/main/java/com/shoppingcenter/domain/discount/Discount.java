package com.shoppingcenter.domain.discount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Discount {

    public enum Type {
        PERCENTAGE, FIXED_AMOUNT
    }

    private long id;

    private long shopId;

    private String title;

    private double value;

    private Type type;

    private Long totalProduct;

    private Long createdAt;
}
