package com.marketplace.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSetting {

	private boolean cashOnDelivery;

	private boolean bankTransfer;
	
	private String orderNote;

}
