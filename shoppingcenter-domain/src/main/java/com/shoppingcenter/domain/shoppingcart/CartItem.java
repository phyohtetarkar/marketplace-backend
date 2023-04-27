package com.shoppingcenter.domain.shoppingcart;

import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {

    private long id;

    private int quantity;

    private Product product;

    private ProductVariant variant;
    
    private User user;

    public CartItem() {
    }

}
