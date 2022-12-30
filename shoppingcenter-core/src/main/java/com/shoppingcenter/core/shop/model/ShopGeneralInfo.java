package com.shoppingcenter.core.shop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopGeneralInfo {
    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;
}
