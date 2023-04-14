package com.shoppingcenter.app.controller.product.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantDTO {

    private long id;

    private Long productId;

    private String variant;

    private String title;

    private String sku;

    private Double cost;

    private double price;

    private int stockLeft;

    private List<ProductVariantOptionDTO> options;

    @JsonProperty(access = Access.WRITE_ONLY)
    private boolean deleted;
}
