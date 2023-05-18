package com.shoppingcenter.domain.misc;

public interface OTPAttemptDao {

	void save(OTPAttempt attempt);
	
	OTPAttempt getAttempt(String phone, String date);
	
}
