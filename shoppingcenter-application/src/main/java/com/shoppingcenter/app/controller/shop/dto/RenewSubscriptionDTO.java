package com.shoppingcenter.app.controller.shop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewSubscriptionDTO {

	@JsonIgnore
	private long userId;

	@JsonIgnore
	private long shopId;
	
	private long subscriptionPlanId;
	
	private Long promoCodeId;
	
}
