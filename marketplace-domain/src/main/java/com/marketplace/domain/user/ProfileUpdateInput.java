package com.marketplace.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateInput {
	
	private long userId;
	
	private String name;
	
	private String phone;
	
}
