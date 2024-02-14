package com.marketplace.api.consumer.review;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.modelmapper.TypeToken;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.user.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewDTO {

    private BigDecimal rating;

    private String description;

    private UserDTO reviewer;

    private long createdAt;

    public static Type pagType() {
        return new TypeToken<PageDataDTO<ShopReviewDTO>>() {
        }.getType();
    }
}
