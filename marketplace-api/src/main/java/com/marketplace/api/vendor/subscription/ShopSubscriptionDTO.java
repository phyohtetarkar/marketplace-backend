package com.marketplace.api.vendor.subscription;

import java.math.BigDecimal;

import com.marketplace.domain.subscription.ShopSubscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionDTO {

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

}
