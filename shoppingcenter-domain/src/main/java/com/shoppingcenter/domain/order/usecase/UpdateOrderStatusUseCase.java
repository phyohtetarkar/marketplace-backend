package com.shoppingcenter.domain.order.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.Order.Status;
import com.shoppingcenter.domain.order.dao.OrderDao;

public class UpdateOrderStatusUseCase {
	
	private OrderDao orderDao;

	public void apply(String code, Order.Status status) {
		
		var order = orderDao.findByCode(code);
		
		if (order == null) {
			throw new ApplicationException("Order not found");
		}
		
		orderDao.updateStatus(order.getId(), status);
		
		if (status == Status.CANCELLED) {
		}
		
		if (status == Status.CONFIRMED) {
		}
		
		if (status == Status.COMPLETED) {
			// TODO : create sale history record
			
		}
		
	}
}
