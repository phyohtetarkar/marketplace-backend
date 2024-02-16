package com.marketplace.app.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUser {

	private String uid;

	private String name;

	private String email;

	private String imageUrl;
	
	private boolean disabled;

}
