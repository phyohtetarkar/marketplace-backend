package com.shoppingcenter.app.controller.shoppingcart.dto;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductVariantDTO;

@Getter
@Setter
public class CartItemDTO {
    private long id;

    private long productId;

    private Long variantId;

    private int quantity;

    private ProductDTO product;

    private ProductVariantDTO variant;

    public static Type listType() {
        return new TypeToken<List<CartItemDTO>>() {
        }.getType();
    }
}
