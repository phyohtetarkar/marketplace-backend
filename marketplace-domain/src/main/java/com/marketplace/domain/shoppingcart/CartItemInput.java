package com.marketplace.domain.shoppingcart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemInput {

	private int quantity;
	
	private long userId;
	
	private long productId;
	
	private long variantId;
	
}
