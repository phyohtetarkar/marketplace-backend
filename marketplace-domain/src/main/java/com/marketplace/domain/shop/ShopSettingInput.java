package com.marketplace.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSettingInput {

	private long shopId;

	private boolean cashOnDelivery;

	private boolean bankTransfer;
	
	private String orderNote;
	
}
