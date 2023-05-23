package com.shoppingcenter.domain.shop;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscription {
	
	public enum Status {
		PROCESSING, SUCCESS, FAILED
	}

	private long id;
	
	private String title;

	private BigDecimal subTotalPrice;

	private BigDecimal discount;

	private BigDecimal totalPrice;
	
	private ShopSubscription.Status status;
	
	private int duration;

	private long startAt;
	
	private long endAt;
	
	private boolean preSubscription;
	
	private Shop shop;
	
	private long shopId;
	
	private long createdAt;
	
	public ShopSubscription() {
		this.status = Status.PROCESSING;
		this.subTotalPrice =  BigDecimal.valueOf(0);
		this.totalPrice =  BigDecimal.valueOf(0);
		this.discount = BigDecimal.valueOf(0);
	}
	
}
