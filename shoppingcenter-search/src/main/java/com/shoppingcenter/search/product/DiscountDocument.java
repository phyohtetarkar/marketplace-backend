package com.shoppingcenter.search.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDocument {

    private long id;

    private String title;

    private double value;

    private String type;

}
