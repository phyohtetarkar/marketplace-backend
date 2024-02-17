package com.marketplace.domain.subscription;

import java.math.BigDecimal;

import com.marketplace.domain.Audit;
import com.marketplace.domain.shop.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscription {
	
	public enum Status {
		PENDING, SUCCESS, FAILED
	}

	private long invoiceNo;
	
	private String title;

	private BigDecimal subTotalPrice;

	private BigDecimal discount;

	private BigDecimal totalPrice;
	
	private ShopSubscription.Status status;
	
	private int duration;

	private long startAt;
	
	private long endAt;
	
	private String promoCode;
	
	private Shop shop;
	
	private ShopSubscriptionTransaction transaction;
	
	private Audit audit = new Audit();
	
	public ShopSubscription() {
		this.subTotalPrice =  BigDecimal.ZERO;
		this.totalPrice =  BigDecimal.ZERO;
		this.discount = BigDecimal.ZERO;
	}
	
}
