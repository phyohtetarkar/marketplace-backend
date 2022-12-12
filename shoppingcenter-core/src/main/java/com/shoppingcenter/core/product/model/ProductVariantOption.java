package com.shoppingcenter.core.product.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantOption {

	private String option;

	private String value;

	public ProductVariantOption() {
	}

	public ProductVariantOption(String option, String value) {
		this.option = option;
		this.value = value;
	}

}
