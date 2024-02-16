package com.marketplace.domain.order.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.order.Order.Status;
import com.marketplace.domain.order.OrderStatusHistory;
import com.marketplace.domain.order.dao.OrderDao;
import com.marketplace.domain.order.dao.OrderStatusHistoryDao;

@Component
public class CancelOrderByBuyerUseCase {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderStatusHistoryDao orderStatusHistoryDao;

	@Transactional
	public void apply(long userId, long orderId) {
		var order = orderDao.findById(orderId);

		if (order == null) {
			throw new ApplicationException("Order not found");
		}
		
		if (order.getCustomer().getId() != userId) {
			throw new ApplicationException("Order not found");
		}
		
		if (order.getStatus() == Status.COMPLETED) {
			throw new ApplicationException("You cannot cancel completed order");
		}
		
		if (order.getStatus() == Status.CONFIRMED) {
			throw new ApplicationException("You cannot cancel confirmed order");
		}

		orderDao.updateStatus(orderId, Status.CANCELLED);
		
		var history = new OrderStatusHistory();
		history.setOrderId(orderId);
		history.setStatus(Status.CANCELLED);
		history.setRemark("Order cancelled");
		
		orderStatusHistoryDao.save(history);
	}

}
