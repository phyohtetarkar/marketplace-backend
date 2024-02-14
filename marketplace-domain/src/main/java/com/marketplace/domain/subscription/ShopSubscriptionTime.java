package com.marketplace.domain.subscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionTime {

	private long invoiceNo;
	
	private long shopId;
	
	private long startAt;
	
	private long endAt;
	
}
