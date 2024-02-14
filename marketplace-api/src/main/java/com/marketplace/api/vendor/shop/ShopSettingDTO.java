package com.marketplace.api.vendor.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSettingDTO {

	@JsonIgnore
	private long shopId;

	private boolean cashOnDelivery;

	private boolean bankTransfer;
	
	private String orderNote;
	
}
