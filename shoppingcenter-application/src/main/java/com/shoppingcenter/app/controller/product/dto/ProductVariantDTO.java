package com.shoppingcenter.app.controller.product.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantDTO {

    private String id;

    private long productId;

    private String title;

    private String sku;

    private double price;

    private boolean outOfStock;

    private List<ProductVariantOptionDTO> options;

    @JsonProperty(access = Access.WRITE_ONLY)
    private boolean deleted;
}
