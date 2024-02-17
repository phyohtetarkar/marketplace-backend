package com.marketplace.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilter {

	private List<String> brands;

	private BigDecimal maxPrice;

	public ProductFilter() {
		this.brands = new ArrayList<String>();
		this.maxPrice = BigDecimal.valueOf(10000);
	}
	
	public BigDecimal getMaxPrice() {
		if (maxPrice == null) {
			return BigDecimal.valueOf(10000);
		}
		
		return maxPrice;
	}
}
