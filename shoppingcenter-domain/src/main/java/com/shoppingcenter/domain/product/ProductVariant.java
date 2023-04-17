package com.shoppingcenter.domain.product;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariant {

	private long id;

	private long productId;

	private String title;

	private String sku;

	private double price;

	private int stockLeft;

	private List<ProductVariantOption> options;

	private boolean deleted;

	public ProductVariant() {
		this.options = new ArrayList<>();
	}
}
