package com.shoppingcenter.app.controller.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {

    private String id;

    private long productId;

    private String name;

    private boolean thumbnail;
}
