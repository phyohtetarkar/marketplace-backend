package com.shoppingcenter.domain.common;

public interface OTPVerificationAdapter {

	OTPVerification request(String phone);
	
	OTPVerification verify(String code, int requestId);
	
}
