package com.shoppingcenter.app.controller.shoppingcart.dto;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

@Getter
@Setter
public class CartItemDTO {
    private String userId;

    private long productId;

    private String optionPath;

    private int quantity;

    // private Product product;

    // private ProductVariant variant;

    @Getter
    @Setter
    public static class ID {
        private String userId;

        private long productId;

        private String optionPath;
    }

    public static Type listType() {
        return new TypeToken<List<CartItemDTO>>() {
        }.getType();
    }
}
