package com.shoppingcenter.domain.order.dao;

import java.util.List;

import com.shoppingcenter.domain.order.OrderItem;

public interface OrderItemDao {

	void createAll(List<OrderItem> items);
	
	void removeProductRelation(long productId);
	
	boolean exists(long id);
	
	OrderItem findById(long id);
	
	List<OrderItem> findByOrder(long orderId);
	
}
