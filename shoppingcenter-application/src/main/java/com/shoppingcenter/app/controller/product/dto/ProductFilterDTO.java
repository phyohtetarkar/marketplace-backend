package com.shoppingcenter.app.controller.product.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterDTO {

	private List<String> brands;
	
	private BigDecimal maxPrice;
	
	public ProductFilterDTO() {
		this.brands = new ArrayList<String>();
		this.maxPrice = BigDecimal.valueOf(10000);
	}
	
	
	public void setMaxPrice(BigDecimal maxPrice) {
		if (maxPrice != null) {
			this.maxPrice = maxPrice;
		}
	}
}
