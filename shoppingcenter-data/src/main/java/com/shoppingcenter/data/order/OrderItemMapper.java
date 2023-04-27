package com.shoppingcenter.data.order;

import com.shoppingcenter.domain.order.OrderItem;

public class OrderItemMapper {
	
	public static OrderItem toDomain(OrderItemEntity entity) {
		var item = toDomainCompat(entity);
		item.setOrderId(entity.getOrder().getId());
		return item;
	}

	public static OrderItem toDomainCompat(OrderItemEntity entity) {
		var item = new OrderItem();
		item.setId(entity.getId());
		item.setProductId(entity.getProductId());
		item.setUnitPrice(entity.getUnitPrice());
		item.setDiscount(entity.getDiscount());
		item.setQuantity(entity.getQuantity());
		item.setProductName(entity.getProductName());
		item.setProductImage(entity.getProductImage());
		item.setProductSlug(entity.getProductSlug());
		item.setRemoved(entity.isRemoved());
		item.setVariant(entity.getVariant());
		return item;
	}
	
}
