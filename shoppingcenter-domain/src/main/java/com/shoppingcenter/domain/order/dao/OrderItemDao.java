package com.shoppingcenter.domain.order.dao;

import java.util.List;

import com.shoppingcenter.domain.order.OrderItem;

public interface OrderItemDao {

	void createAll(List<OrderItem> items);
	
	void updateRemoved(long id, boolean removed);
	
	boolean exists(long id);
	
	OrderItem findById(long id);
	
}
