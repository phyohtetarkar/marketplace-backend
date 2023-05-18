package com.shoppingcenter.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberVerify {

	private long userId;

	private String code;

	private int requestId;
	
}
