package com.shoppingcenter.app.controller.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDTO {

	private String phone;

	private String password;

	private String code;

}
