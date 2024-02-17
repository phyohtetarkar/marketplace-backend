package com.marketplace.api.vendor.subscription;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewSubscriptionDTO {

	@JsonIgnore
	private long shopId;
	
	private long subscriptionPlanId;
	
	private Long promoCodeId;
	
}
