package com.shoppingcenter.core.shoppingcart.model;

import com.shoppingcenter.core.product.model.Product;
import com.shoppingcenter.core.product.model.ProductVariant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {

    private String id;

    private String userId;

    private long productId;

    private String variantId;

    private int quantity;

    private Product product;

    private ProductVariant variant;

    public CartItem() {
    }

}
