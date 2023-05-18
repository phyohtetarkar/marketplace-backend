package com.shoppingcenter.domain.shop;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMonthlySale {

	private long shopId;

	private int year;

	private int month;

	private BigDecimal totalSale;

	public ShopMonthlySale() {
		this.totalSale = new BigDecimal(0);
	}

}
