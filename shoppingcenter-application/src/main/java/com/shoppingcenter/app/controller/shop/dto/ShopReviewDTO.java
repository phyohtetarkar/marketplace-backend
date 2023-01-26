package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.service.PageData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewDTO {

    private long id;

    private long shopId;

    private double rating;

    private String description;

    private UserDTO reviewer;

    private long createdAt;

    public static Type pagType() {
        return new TypeToken<PageData<ShopReviewDTO>>() {
        }.getType();
    }
}
