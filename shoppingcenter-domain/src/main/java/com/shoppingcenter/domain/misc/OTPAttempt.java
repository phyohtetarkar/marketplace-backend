package com.shoppingcenter.domain.misc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPAttempt {
	
	private String date;
	
	private String phone;

	private int attempt;
	
}
