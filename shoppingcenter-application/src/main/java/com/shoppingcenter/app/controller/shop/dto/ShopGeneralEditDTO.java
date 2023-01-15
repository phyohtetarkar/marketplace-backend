package com.shoppingcenter.app.controller.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopGeneralEditDTO {
    private long shopId;

    private String name;

    private String slug;

    private String headline;

    private String about;
}
