package com.shoppingcenter.domain.product;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAttribute {

	private long id;
	
	private String name;
	
	private int sort;
	
	private Set<ProductAttributeValue> values;
	
	public ProductAttribute() {
		this.values = new HashSet<>();
	}
	
}
