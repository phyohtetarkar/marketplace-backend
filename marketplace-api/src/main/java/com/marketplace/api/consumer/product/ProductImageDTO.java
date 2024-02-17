package com.marketplace.api.consumer.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.ProductImageSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {

    private long id;

    @JsonSerialize(using = ProductImageSerializer.class)
    private String name;

    private boolean thumbnail;
}
