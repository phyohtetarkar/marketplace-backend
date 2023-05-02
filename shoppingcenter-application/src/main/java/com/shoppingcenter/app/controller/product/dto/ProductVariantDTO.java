package com.shoppingcenter.app.controller.product.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantDTO {

    private long id;

    private String sku;

    private BigDecimal price;

    private int stockLeft;

    private List<ProductVariantAttributeDTO> attributes;

    @JsonProperty(access = Access.WRITE_ONLY)
    private boolean deleted;
}
