package com.marketplace.api.consumer.shoppingcart;

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
}
