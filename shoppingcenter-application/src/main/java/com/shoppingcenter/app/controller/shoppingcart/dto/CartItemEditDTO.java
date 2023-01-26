package com.shoppingcenter.app.controller.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemEditDTO {

    private long id;

    private long productId;

    private Long variantId;

    @JsonIgnore
    private String userId;

    private int quantity;

}
