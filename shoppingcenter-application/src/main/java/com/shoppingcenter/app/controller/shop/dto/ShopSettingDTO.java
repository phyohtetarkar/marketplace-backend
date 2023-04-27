package com.shoppingcenter.app.controller.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSettingDTO {

	private long shopId;

	private boolean cashOnDelivery;

	private boolean bankTransfer;
	
}
