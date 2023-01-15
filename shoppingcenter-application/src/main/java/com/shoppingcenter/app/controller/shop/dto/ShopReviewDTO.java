package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;

import com.shoppingcenter.core.PageData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewDTO {
    private long shopId;

    private String userId;

    private double rating;

    private String description;

    // private User reviewer;

    private long createdAt;

    public static Type pagType() {
        return new TypeToken<PageData<ShopReviewDTO>>() {
        }.getType();
    }
}
