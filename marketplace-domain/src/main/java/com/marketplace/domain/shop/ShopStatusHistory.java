package com.marketplace.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopStatusHistory {

	private long id;
	
	private Shop.Status status;
	
	private String remark;
	
	private long shopId;
	
}
