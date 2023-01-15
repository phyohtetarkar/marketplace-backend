package com.shoppingcenter.app.controller.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemEditDTO {

    private long productId;

    private String optionPath;

    @JsonIgnore
    private String userId;

    private int quantity;

}
