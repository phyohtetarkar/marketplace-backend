package com.marketplace.domain.subscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewShopSubscriptionInput {

	private long shopId;
	
	private long subscriptionPlanId;
	
	private Long promoCodeId;
	
}
