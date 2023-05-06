package com.shoppingcenter.app.controller.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDetailDTO {

	private long orderId;

	private String name;

	private String phone;
	
	private String city;

	private String address;
	
}
