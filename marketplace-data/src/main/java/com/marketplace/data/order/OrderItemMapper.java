package com.marketplace.data.order;

import com.marketplace.data.product.ProductMapper;
import com.marketplace.domain.order.OrderItem;

public class OrderItemMapper {

	public static OrderItem toDomain(OrderItemEntity entity) {
		var item = new OrderItem();
		item.setId(entity.getId());
		item.setUnitPrice(entity.getUnitPrice());
		item.setDiscountPrice(entity.getDiscountPrice());
		item.setQuantity(entity.getQuantity());
		item.setCancelled(entity.isCancelled());
		
		item.setProduct(ProductMapper.toDomainCompat(entity.getProduct()));
		item.setOrderId(entity.getOrder().getId());
		
		if (entity.getProductVariant() != null) {
			item.setProductVariant(ProductMapper.toVariant(entity.getProductVariant()));
		}
		return item;
	}
	
}
