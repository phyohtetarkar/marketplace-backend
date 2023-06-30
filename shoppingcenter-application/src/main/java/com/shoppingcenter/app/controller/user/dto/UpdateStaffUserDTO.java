package com.shoppingcenter.app.controller.user.dto;

import java.util.List;

import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserPermission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStaffUserDTO {

	private String phone;
	
	private User.Role role;
	
	private List<UserPermission.Permission> permissions;
	
}
