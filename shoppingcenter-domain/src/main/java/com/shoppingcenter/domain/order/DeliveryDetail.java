package com.shoppingcenter.domain.order;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDetail {

	private long orderId;

	private String name;

	private String phone;
	
	private String city;

	private String address;

	public void validate() {
		if (!Utils.hasText(name)) {
			throw new ApplicationException("Invalid delivery info");
		}

		if (!Utils.isPhoneNumber(phone)) {
			throw new ApplicationException("Invalid delivery info");
		}
		
		if (!Utils.hasText(city)) {
			throw new ApplicationException("Invalid delivery info");
		}

		if (!Utils.hasText(address)) {
			throw new ApplicationException("Invalid delivery info");
		}
	}
}
