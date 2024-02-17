package com.marketplace.domain.product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariant {

	private long id;

	private String sku;

	private BigDecimal price;

	private boolean available;
	
	private boolean deleted;

	private Set<ProductVariantAttribute> attributes;

	public ProductVariant() {
		this.attributes = new HashSet<>();
	}
	
}
