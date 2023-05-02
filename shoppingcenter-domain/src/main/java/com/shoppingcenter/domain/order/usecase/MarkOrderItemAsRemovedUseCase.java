package com.shoppingcenter.domain.order.usecase;

import java.math.BigDecimal;

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
		
		var subTotalPrice = BigDecimal.valueOf(0);
		var totalPrice = BigDecimal.valueOf(0);
		var discount = BigDecimal.valueOf(0);
		var quantity = 0;
		
		for (var orderItem : orderItems) {
			if (orderItem.isRemoved()) {
				continue;
			}
			
			subTotalPrice = subTotalPrice.add(orderItem.getSubTotalPrice());
			totalPrice = totalPrice.add(orderItem.getTotalPrice());
			discount = discount.add(orderItem.getDiscount());
			quantity += orderItem.getQuantity();
			
		}
		
		order.setSubTotalPrice(subTotalPrice);
		order.setTotalPrice(totalPrice);
		order.setDiscount(discount);
		order.setQuantity(quantity);
		
		orderDao.update(order);
		
	}
	
}
