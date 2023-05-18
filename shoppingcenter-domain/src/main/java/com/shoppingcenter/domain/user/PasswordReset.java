package com.shoppingcenter.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordReset {

	private String phone;
	
	private String password;
	
	private String code;
	
	private int requestId; 
	
}
