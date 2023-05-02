package com.shoppingcenter.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantAttribute {

	private String value;

	private int sort;

	public ProductVariantAttribute() {
	}

	public ProductVariantAttribute(String value, int sort) {
		super();
		this.value = value;
		this.sort = sort;
	}

}
