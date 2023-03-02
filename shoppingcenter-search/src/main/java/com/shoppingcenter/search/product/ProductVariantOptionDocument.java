package com.shoppingcenter.search.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantOptionDocument {

	private String option;

	private String value;

	public ProductVariantOptionDocument() {
	}

	public ProductVariantOptionDocument(String option, String value) {
		this.option = option;
		this.value = value;
	}

}
