package com.shoppingcenter.domain.common;

public interface PasswordEncoderAdapter {

	String encode(String rawPassword);
	
	boolean matches(String rawPassword, String encodedPassword);
	
}
