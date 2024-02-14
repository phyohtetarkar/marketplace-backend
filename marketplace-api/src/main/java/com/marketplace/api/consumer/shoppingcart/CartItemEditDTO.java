package com.marketplace.api.consumer.shoppingcart;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemEditDTO {

    @JsonIgnore
    private long userId;

    private long productId;

    private long variantId;

    private int quantity;

}
