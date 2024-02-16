package com.marketplace.domain.order.usecase;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.dao.OrderDao;
import com.marketplace.domain.order.dao.OrderItemDao;
import com.marketplace.domain.shop.dao.ShopMemberDao;

@Component
public class CancelOrderItemUseCase {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ShopMemberDao shopMemberDao;

	@Autowired
	private OrderItemDao orderItemDao;
	
	@Transactional
	public void apply(long userId, long orderItemId) {
		var orderItem = orderItemDao.findById(orderItemId);
		
		if (orderItem == null) {
			throw new ApplicationException("Order item not found");
		}
		
		var order = orderDao.findById(orderItem.getId());
		
		if (order == null) {
			throw new ApplicationException("Order item not found");
		}
		
		if (!shopMemberDao.existsByShopAndUser(order.getShop().getId(), userId)) {
			throw new ApplicationException("Order item not found");
		}
		
		if (order.getStatus() == Order.Status.COMPLETED || order.getStatus() == Order.Status.CANCELLED) {
			throw new ApplicationException("Unable to cancel order item");
		}
		
		if (orderItemDao.countByOrderCancelledFalse(orderItem.getOrderId()) <= 1) {
			throw new ApplicationException("Unable to cancel order item");
		}
		
		orderItemDao.updateCancelled(orderItemId, true);
		
		var subTotalPrice = BigDecimal.valueOf(0);
		var totalPrice = BigDecimal.valueOf(0);
		var discount = BigDecimal.valueOf(0);
		var quantity = 0;
		
		for (var item : order.getItems()) {
			if (item.getId() == orderItemId) {
				continue;
			}
			
			if (item.isCancelled()) {
				continue;
			}
			
			subTotalPrice = subTotalPrice.add(item.getSubTotalPrice());
			totalPrice = totalPrice.add(item.getTotalPrice());
			discount = discount.add(item.getDiscountPrice());
			quantity += item.getQuantity();
		}
		
		order.setSubTotalPrice(subTotalPrice);
		order.setTotalPrice(totalPrice);
		order.setDiscountPrice(discount);
		order.setQuantity(quantity);
		
		orderDao.update(order);
	}
	
}
