package com.marketplace.api.consumer.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantAttributeDTO {
	
	private String attribute;

    private String value;
    
    private int sort;
    
    @JsonProperty("vSort")
    private int vSort;
}
