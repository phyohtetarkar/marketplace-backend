package com.shoppingcenter.app.controller.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemDTO {

	private long id;
	
	@JsonIgnore
	private long userId;
	
	private int quantity;
	
}
