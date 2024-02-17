package com.marketplace.api.consumer.shoppingcart;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.marketplace.api.consumer.product.ProductDTO;
import com.marketplace.api.consumer.product.ProductVariantDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
	
	private long userId;

    private int quantity;

    private ProductDTO product;

    private ProductVariantDTO variant;

    public CartItemDTO() {
    }

    public static Type listType() {
        return new TypeToken<List<CartItemDTO>>() {
        }.getType();
    }
}
