package com.shoppingcenter.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantAttribute {

	private long attributeId;
	
	private String attribute;

	private String value;

	private int sort;

	public ProductVariantAttribute() {
	}

}
