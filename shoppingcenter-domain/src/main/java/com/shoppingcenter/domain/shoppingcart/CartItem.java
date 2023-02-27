package com.shoppingcenter.domain.shoppingcart;

import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductVariant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {

    private long id;

    private String userId;

    private int quantity;

    private Product product;

    private ProductVariant variant;

    private long productId;

    private Long variantId;

}
