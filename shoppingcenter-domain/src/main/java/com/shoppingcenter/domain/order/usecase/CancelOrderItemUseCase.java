package com.shoppingcenter.domain.order.usecase;

import java.math.BigDecimal;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.order.dao.OrderItemDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

import lombok.Setter;

@Setter
public class CancelOrderItemUseCase {
	
	private OrderDao orderDao;

	private OrderItemDao orderItemDao;
	
	private ShopMemberDao shopMemberDao;
	
	private ProductDao productDao;
	
	private ProductVariantDao productVariantDao;
	
	public void apply(long userId, long orderItemId) {
		var orderItem = orderItemDao.findById(orderItemId);
		
		if (orderItem == null) {
			throw new ApplicationException("Order item not found");
		}
		
		if (!shopMemberDao.existsByShopAndUser(orderItem.getOrder().getShopId(), userId)) {
			throw new ApplicationException("Order item not found");
		}
		
		if (orderItemDao.countByOrderCancelledFalse(orderItem.getOrderId()) <= 1) {
			throw new ApplicationException("Unable to cancel order item");
		}
		
		orderItemDao.updateCancelled(orderItemId, true);
		
		if (orderItem.getProductVariantId() != null) {
			var productVariant = productVariantDao.findById(orderItem.getProductVariantId());
			
			if (productVariant == null) {
				return;
			}
			
			var stockLeft = productVariant.getStockLeft() + orderItem.getQuantity();
			productVariantDao.updateStockLeft(productVariant.getId(), stockLeft);
		} else {
			var product = productDao.findById(orderItem.getProductId());
			
			if (product == null) {
				return;
			}
			
			var stockLeft = product.getStockLeft() + orderItem.getQuantity();
			productDao.updateStockLeft(product.getId(), stockLeft);
		}
		
		var order = orderDao.findById(orderItem.getOrderId());
		
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
			discount = discount.add(item.getDiscount());
			quantity += item.getQuantity();
		}
		
		order.setSubTotalPrice(subTotalPrice);
		order.setTotalPrice(totalPrice);
		order.setDiscount(discount);
		order.setQuantity(quantity);
		
		orderDao.update(order);
	}
	
}
