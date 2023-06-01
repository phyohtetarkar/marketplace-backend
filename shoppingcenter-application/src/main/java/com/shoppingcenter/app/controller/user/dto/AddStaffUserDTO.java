package com.shoppingcenter.app.controller.user.dto;

import com.shoppingcenter.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddStaffUserDTO {

	private String phone;
	
	private User.Role role;
	
}
