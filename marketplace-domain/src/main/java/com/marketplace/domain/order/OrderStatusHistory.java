package com.marketplace.domain.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusHistory {

	private long id;
	
	private Order.Status status;
	
	private String remark;
	
	private long orderId;
	
}
