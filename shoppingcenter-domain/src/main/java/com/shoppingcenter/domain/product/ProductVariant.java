package com.shoppingcenter.domain.product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariant {

	private long id;

	private long productId;

	private String sku;

	private BigDecimal price;

	private int stockLeft;

	private Set<ProductVariantAttribute> attributes;

	private boolean deleted;

	public ProductVariant() {
		this.attributes = new HashSet<>();
	}
	
}
