package com.marketplace.domain.user;

import com.marketplace.domain.user.User.Permission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPermission {
	
	private User.Permission permission;
	
	public UserPermission() {
	}

	public UserPermission(Permission permission) {
		this.permission = permission;
	}
	
}
