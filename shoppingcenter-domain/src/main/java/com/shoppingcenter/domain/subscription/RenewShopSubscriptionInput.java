package com.shoppingcenter.domain.subscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewShopSubscriptionInput {
	
	private long userId;

	private long shopId;
	
	private long subscriptionPlanId;
	
	private Long promoCodeId;
	
}
