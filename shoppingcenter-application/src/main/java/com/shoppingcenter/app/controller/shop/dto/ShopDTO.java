package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDTO {
    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;

    private double rating;

    private long createdAt;

    private ShopContactDTO contact;

    private Boolean featured;

    private String logo;

    private String cover;

    private Shop.Status status;

    public static Type listType() {
        return new TypeToken<List<ShopDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageData<ShopDTO>>() {
        }.getType();
    }
}
