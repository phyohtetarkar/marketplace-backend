package com.shoppingcenter.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberUpdate {

	private long userId;
	
	private String phone;
	
	private String code;
	
	private int requestId;
	
}
