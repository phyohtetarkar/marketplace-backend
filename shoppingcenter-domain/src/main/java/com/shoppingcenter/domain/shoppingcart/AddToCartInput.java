package com.shoppingcenter.domain.shoppingcart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartInput {

	private int quantity;
	
	private long userId;
	
	private long productId;
	
	private Long variantId;
	
}
