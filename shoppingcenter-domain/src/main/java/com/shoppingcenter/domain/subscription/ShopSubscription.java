package com.shoppingcenter.domain.subscription;

import java.math.BigDecimal;

import com.shoppingcenter.domain.shop.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscription {
	
	public enum Status {
		PENDING, SUCCESS, FAILED
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
	
	private String promoCode;
	
	private Shop shop;
	
	private long shopId;
	
	private long createdAt;
	
	private ShopSubscriptionTransaction transaction;
	
	public ShopSubscription() {
		this.subTotalPrice =  BigDecimal.valueOf(0);
		this.totalPrice =  BigDecimal.valueOf(0);
		this.discount = BigDecimal.valueOf(0);
	}
	
}
