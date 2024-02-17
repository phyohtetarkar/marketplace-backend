package com.marketplace.domain.shop;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMonthlySale {

	private int year;

	private int month;

	private BigDecimal totalSale;

	public ShopMonthlySale() {
		this.totalSale = BigDecimal.ZERO;
	}

}
