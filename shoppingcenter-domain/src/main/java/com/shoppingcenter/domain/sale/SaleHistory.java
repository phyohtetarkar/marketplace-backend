package com.shoppingcenter.domain.sale;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleHistory {
	
	private int year;
	
	private int month;

	private BigDecimal totalSale;
	
	public SaleHistory() {
		this.totalSale = new BigDecimal(0);
	}
	
}
