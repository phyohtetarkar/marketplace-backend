package com.shoppingcenter.core.shop.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopContact {
    private long id;

    private List<String> phones;

    private String address;

    private double latitude;

    private double longitude;
}
