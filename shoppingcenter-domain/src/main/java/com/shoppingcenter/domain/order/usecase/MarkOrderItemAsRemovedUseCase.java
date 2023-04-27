package com.shoppingcenter.domain.order.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.order.dao.OrderItemDao;

import lombok.Setter;

@Setter
public class MarkOrderItemAsRemovedUseCase {
	
	private OrderItemDao orderItemDao;
	
	private OrderDao orderDao;

	public void apply(long id) {
		var item = orderItemDao.findById(id);
		if (item == null) {
			throw new ApplicationException("Order item not found");
		}
		
		orderItemDao.updateRemoved(id, true);
		
		var order = orderDao.findById(item.getOrderId());
		
		var orderItems = order.getItems();
		
		var subTotalPrice = 0;
		var totalPrice = 0;
		var discount = 0;
		var quantity = 0;
		
		for (var orderItem : orderItems) {
			if (orderItem.isRemoved()) {
				continue;
			}
			
			subTotalPrice += orderItem.getSubTotalPrice();
			totalPrice += orderItem.getTotalPrice();
			discount += orderItem.getDiscount();
			quantity += orderItem.getQuantity();
			
		}
		
		order.setSubTotalPrice(subTotalPrice);
		order.setTotalPrice(totalPrice);
		order.setDiscount(discount);
		order.setQuantity(quantity);
		
		orderDao.update(order);
		
	}
	
}
