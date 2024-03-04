package com.marketplace.api.vendor.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMonthlySaleDTO {
	
	private int year;

	private int month;

	private double totalSale;

}
