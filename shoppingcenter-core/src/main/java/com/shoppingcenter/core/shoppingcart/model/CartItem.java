package com.shoppingcenter.core.shoppingcart.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.core.product.model.Product;
import com.shoppingcenter.core.product.model.ProductVariant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {

    private Id id;

    private int quantity;

    @JsonProperty(access = Access.READ_ONLY)
    private Product product;

    @JsonProperty(access = Access.READ_ONLY)
    private ProductVariant variant;

    public CartItem() {
        this.id = new Id();
    }

    @Getter
    @Setter
    public static class Id {

        private String userId;

        private long productId;

        private Long variantId;

    }

}
