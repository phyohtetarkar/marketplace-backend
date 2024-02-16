package com.marketplace.domain.shop;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopRating {
	
	private long shopId;

	private BigDecimal rating;
	
	private int count;
	
	public ShopRating() {
		this.rating = BigDecimal.ZERO;
	}
	
}
