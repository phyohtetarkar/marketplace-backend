package com.marketplace.api.consumer.product;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterDTO {

	private List<String> brands;
	
	private BigDecimal maxPrice;
}
