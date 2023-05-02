package com.shoppingcenter.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAttributeValue {

	private String value;

	private int sort;

	public ProductAttributeValue() {
	}

	public ProductAttributeValue(String value, int sort) {
		super();
		this.value = value;
		this.sort = sort;
	}

}
