package com.shoppingcenter.app.controller.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartDTO {

    @JsonIgnore
    private long userId;

    private long productId;

    private Long variantId;

    private int quantity;

}
