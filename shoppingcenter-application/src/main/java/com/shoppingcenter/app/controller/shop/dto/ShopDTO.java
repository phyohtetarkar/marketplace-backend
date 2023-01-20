package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;
import java.util.List;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.data.shop.ShopEntity.Status;

import org.modelmapper.TypeToken;

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

    private boolean featured;

    private String logo;

    private String cover;

    private Status status;

    public static Type listType() {
        return new TypeToken<List<ShopDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageData<ShopDTO>>() {
        }.getType();
    }
}
