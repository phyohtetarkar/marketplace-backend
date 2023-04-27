package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewDTO {

    private long shopId;

    private double rating;

    private String description;

    private UserDTO reviewer;

    private long createdAt;

    public static Type pagType() {
        return new TypeToken<PageDataDTO<ShopReviewDTO>>() {
        }.getType();
    }
}
