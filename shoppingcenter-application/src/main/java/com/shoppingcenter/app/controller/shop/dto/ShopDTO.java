package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.PageDataDTO;

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
    
    private String deliveryNote;

    private double rating;

    private boolean featured;
    
    private boolean disabled;
    
    private boolean activated;
    
    private boolean expired;

    private String logo;

    private String cover;
    
    private ShopContactDTO contact;
    
    private long createdAt;

    public static Type listType() {
        return new TypeToken<List<ShopDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageDataDTO<ShopDTO>>() {
        }.getType();
    }
}
