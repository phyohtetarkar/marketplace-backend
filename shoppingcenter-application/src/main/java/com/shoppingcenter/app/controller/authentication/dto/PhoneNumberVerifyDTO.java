package com.shoppingcenter.app.controller.authentication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberVerifyDTO {

	@JsonIgnore
	private long userId;

	private String code;

	private int requestId;
	
}
