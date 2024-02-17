package com.marketplace.domain.order.dao;

import java.util.List;

import com.marketplace.domain.order.OrderItem;

public interface OrderItemDao {

	void createAll(long orderId, List<OrderItem> items);
	
	void updateCancelled(long id, boolean cancelled);
	
	boolean exists(long id);
	
	long countByOrderCancelledFalse(long orderId);
	
	OrderItem findById(long id);
	
	List<OrderItem> findByOrder(long orderId);
	
}
