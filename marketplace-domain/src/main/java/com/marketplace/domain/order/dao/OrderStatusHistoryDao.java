package com.marketplace.domain.order.dao;

import com.marketplace.domain.order.OrderStatusHistory;

public interface OrderStatusHistoryDao {

	void save(OrderStatusHistory history);
	
}
