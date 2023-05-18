package com.shoppingcenter.domain.subscription;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlySubscription {

	private int year;
	
	private int month;
	
	private BigDecimal totalAmount;
	
	public MonthlySubscription() {
		this.totalAmount = BigDecimal.valueOf(0);
	}
}
