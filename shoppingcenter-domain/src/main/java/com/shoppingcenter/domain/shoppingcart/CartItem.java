package com.shoppingcenter.domain.shoppingcart;

import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductVariant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {

    private long userId;

    private long productId;

    private long variantId;

    private int quantity;

    private Product product;

    private ProductVariant variant;

    public CartItem() {
    }

}
