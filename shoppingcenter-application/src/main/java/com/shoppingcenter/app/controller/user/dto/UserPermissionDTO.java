package com.shoppingcenter.app.controller.user.dto;

import com.shoppingcenter.domain.user.UserPermission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPermissionDTO {

	private long id;
	
	private UserPermission.Permission permission;
	
}
