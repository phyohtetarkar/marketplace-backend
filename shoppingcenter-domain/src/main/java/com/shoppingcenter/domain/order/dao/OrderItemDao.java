package com.shoppingcenter.domain.order.dao;

import java.util.List;

import com.shoppingcenter.domain.order.OrderItem;

public interface OrderItemDao {

	void createAll(List<OrderItem> items);
	
	void updateCancelled(long id, boolean cancelled);
	
	boolean exists(long id);
	
	long countByOrderCancelledFalse(long orderId);
	
	OrderItem findById(long id);
	
	List<OrderItem> findByOrder(long orderId);
	
}
