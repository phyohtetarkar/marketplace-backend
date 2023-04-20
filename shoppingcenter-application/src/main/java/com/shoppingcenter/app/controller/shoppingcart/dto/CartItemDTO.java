package com.shoppingcenter.app.controller.shoppingcart.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductVariantDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {

    private int quantity;

    private ProductDTO product;

    private ProductVariantDTO productVariant;

    public CartItemDTO() {
    }

    public static Type listType() {
        return new TypeToken<List<CartItemDTO>>() {
        }.getType();
    }
}
