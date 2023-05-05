package com.shoppingcenter.app.controller.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantAttributeDTO {
	
	private long attributeId;
	
	private String attribute;

    private String value;
    
    private int sort;
}
