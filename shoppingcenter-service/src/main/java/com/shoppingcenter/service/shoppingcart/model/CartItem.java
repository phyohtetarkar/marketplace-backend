package com.shoppingcenter.service.shoppingcart.model;

import com.shoppingcenter.service.product.model.Product;
import com.shoppingcenter.service.product.model.ProductVariant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {

    private long id;

    private String userId;

    private long productId;

    private Long variantId;

    private int quantity;

    private Product product;

    private ProductVariant variant;

    public CartItem() {
    }

}
