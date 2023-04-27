package com.shoppingcenter.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSetting {

	private long shopId;

	private boolean cashOnDelivery;

	private boolean bankTransfer;

}
