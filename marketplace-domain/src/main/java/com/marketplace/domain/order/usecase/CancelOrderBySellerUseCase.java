package com.marketplace.domain.order.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.order.Order.Status;
import com.marketplace.domain.order.OrderStatusHistory;
import com.marketplace.domain.order.dao.OrderDao;
import com.marketplace.domain.order.dao.OrderStatusHistoryDao;
import com.marketplace.domain.shop.dao.ShopMemberDao;

@Component
public class CancelOrderBySellerUseCase {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ShopMemberDao shopMemberDao;
	
	@Autowired
	private OrderStatusHistoryDao orderStatusHistoryDao;

	@Transactional
	public void apply(long userId, long orderId) {
		var order = orderDao.findById(orderId);

		if (order == null) {
			throw new ApplicationException("Order not found");
		}
		
		if (!shopMemberDao.existsByShopAndUser(order.getShop().getId(), userId)) {
			throw new ApplicationException("Order not found");
		}
		
		if (order.getStatus() == Status.COMPLETED) {
			throw new ApplicationException("You cannot cancel completed order");
		}

		orderDao.updateStatus(orderId, Status.CANCELLED);
		
		var history = new OrderStatusHistory();
		history.setOrderId(orderId);
		history.setStatus(Status.CANCELLED);
		history.setRemark("Order cancelled");
		
		orderStatusHistoryDao.save(history);
	}

}
