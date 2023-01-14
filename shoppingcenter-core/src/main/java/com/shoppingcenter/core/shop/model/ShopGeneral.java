package com.shoppingcenter.core.shop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopGeneral {
    private long shopId;

    private String name;

    private String slug;

    private String headline;

    private String about;
}
