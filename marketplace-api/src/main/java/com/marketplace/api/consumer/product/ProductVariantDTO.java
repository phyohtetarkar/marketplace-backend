package com.marketplace.api.consumer.product;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantDTO {

    private long id;

    private String sku;

    private BigDecimal price;

    private boolean available;

    private List<ProductVariantAttributeDTO> attributes;
}
