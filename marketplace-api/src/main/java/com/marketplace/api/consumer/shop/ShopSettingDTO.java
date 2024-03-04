package com.marketplace.api.consumer.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSettingDTO {

	private boolean cashOnDelivery;

	private boolean bankTransfer;
	
	private String orderNote;
	
}
