package com.shoppingcenter.domain.order;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderInput {
	
	private long shopId;
	
	private long userId;
    
    private String note;
	
	private Order.PaymentMethod paymentMethod;
	
	private List<Long> cartItems;

	private DeliveryDetail delivery;
	
	private PaymentDetail payment;
	
	public CreateOrderInput() {
		this.cartItems = new ArrayList<Long>();
	}
	
}
