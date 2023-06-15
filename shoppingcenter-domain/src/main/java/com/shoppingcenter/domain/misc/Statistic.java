package com.shoppingcenter.domain.misc;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistic {

	private BigDecimal totalSubscription;
	
	private long totalShop;
	
	private long totalProduct;
	
	private long totalUser;
	
}
