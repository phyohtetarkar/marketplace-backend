package com.shoppingcenter.domain.order.usecase;

import java.math.BigDecimal;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.order.dao.OrderItemDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

import lombok.Setter;

@Setter
public class MarkOrderItemAsRemovedUseCase {
	
	private OrderItemDao orderItemDao;
	
	private OrderDao orderDao;
	
	private ShopMemberDao shopMemberDao;

	public void apply(long userId, long itemId) {
		var item = orderItemDao.findById(itemId);
		if (item == null) {
			throw new ApplicationException("Order item not found");
		}
		
		var order = orderDao.findById(item.getOrderId());
		
		if (order == null) {
			throw new ApplicationException("Order item not found");
		}
		
		var seller = shopMemberDao.existsByShopAndUser(order.getShop().getId(), userId);
		
		if (!seller) {
			throw new ApplicationException("Order item not found");
		}
		
		if (order.getItems().stream().filter(v -> !v.isRemoved()).count() <= 1) {
			throw new ApplicationException("Unable to remove order item");
		}
		
		orderItemDao.updateRemoved(itemId, true);
		
		var orderItems = orderItemDao.findByOrder(order.getId());
		
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
