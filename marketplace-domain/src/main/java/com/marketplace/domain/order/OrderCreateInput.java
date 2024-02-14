package com.marketplace.domain.order;

import java.util.ArrayList;
import java.util.List;

import com.marketplace.domain.shoppingcart.CartItemInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateInput {
	
	private long shopId;
	
	private long userId;
    
    private String note;
	
	private Order.PaymentMethod paymentMethod;
	
	private List<CartItemInput> cartItems;

	private DeliveryDetail delivery;
	
	private PaymentDetail payment;
	
	public OrderCreateInput() {
		this.cartItems = new ArrayList<>();
	}
	
}
