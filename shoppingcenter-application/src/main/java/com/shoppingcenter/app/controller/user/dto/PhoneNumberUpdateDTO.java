package com.shoppingcenter.app.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberUpdateDTO {
	
	@JsonIgnore
	private long userId;

	private String phone;

	private String code;

	private int requestId;
}
