package com.marketplace.domain.order;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDetail {

	private String name;

	private String phone;
	
	private String address;

	public void validate() {
		if (!Utils.hasText(name)) {
			throw new ApplicationException("Invalid delivery info");
		}

		if (!Utils.isPhoneNumber(phone)) {
			throw new ApplicationException("Invalid delivery info");
		}

		if (!Utils.hasText(address)) {
			throw new ApplicationException("Invalid delivery info");
		}
	}
}
