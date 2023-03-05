package com.shoppingcenter.search.product;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantDocument {

	private long id;

	private String title;

	private String sku;

	private double price;

	private int stockLeft;

	private List<ProductVariantOptionDocument> options;

	public ProductVariantDocument() {
		this.options = new ArrayList<>();
	}
}
