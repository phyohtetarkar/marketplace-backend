package com.shoppingcenter.domain.order.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.Order.Status;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

import lombok.Setter;

@Setter
public class CancelOrderUseCase {

	private OrderDao orderDao;
	
	private ShopMemberDao shopMemberDao;
	
	private ProductDao productDao;
	
	private ProductVariantDao productVariantDao;

	public void apply(long userId, long orderId) {
		var order = orderDao.findById(orderId);

		if (order == null) {
			throw new ApplicationException("Order not found");
		}
		
		var seller = shopMemberDao.existsByShopAndUser(order.getShop().getId(), userId);
		
		var buyer = order.getUser().getId() == userId;

		if (!seller && !buyer) {
			throw new ApplicationException("Order not found");
		}
		
		if (order.getStatus() == Status.COMPLETED) {
			throw new ApplicationException("You cannot cancel completed order");
		}
		
		if (order.getStatus() == Status.CONFIRMED && buyer) {
			throw new ApplicationException("You cannot cancel confirmed order");
		}

		orderDao.updateStatus(orderId, Status.CANCELLED);
		
		for (var orderItem : order.getItems()) {
			if (orderItem.getProductVariantId() != null) {
				var productVariant = productVariantDao.findById(orderItem.getProductVariantId());
				
				if (productVariant == null) {
					continue;
				}
				
				var stockLeft = productVariant.getStockLeft() + orderItem.getQuantity();
				productVariantDao.updateStockLeft(productVariant.getId(), stockLeft);
			} else {
				var product = productDao.findById(orderItem.getProductId());
				
				if (product == null) {
					continue;
				}
				
				var stockLeft = product.getStockLeft() + orderItem.getQuantity();
				productDao.updateStockLeft(product.getId(), stockLeft);
			}
		}
	}

}
