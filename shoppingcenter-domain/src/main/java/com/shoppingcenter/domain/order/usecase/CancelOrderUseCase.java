package com.shoppingcenter.domain.order.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.Order.Status;
import com.shoppingcenter.domain.order.dao.OrderDao;

public class CancelOrderUseCase {

	private OrderDao orderDao;

	public CancelOrderUseCase(OrderDao orderDao) {
		super();
		this.orderDao = orderDao;
	}

	public void apply(long userId, long orderId) {
		var order = orderDao.findById(orderId);

		if (order == null) {
			throw new ApplicationException("Order not found");
		}

		if (order.getUser().getId() != userId) {
			throw new ApplicationException("Order not found");
		}
		
		if (order.getStatus() == Status.COMPLETED) {
			throw new ApplicationException("You cannot cancel completed order");
		}
		
		if (order.getStatus() == Status.CONFIRMED) {
			throw new ApplicationException("You cannot cancel confirmed order");
		}

		orderDao.updateStatus(orderId, Status.CANCELLED);
	}

}
