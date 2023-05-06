package com.shoppingcenter.data.order;

import java.util.stream.Collectors;

import com.shoppingcenter.domain.order.OrderItem;
import com.shoppingcenter.domain.product.ProductVariantAttribute;

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
		if (entity.getAttributes() != null) {
			var attributes = entity.getAttributes().stream().map(a -> {
	        	var va = new ProductVariantAttribute();
	        	va.setAttributeId(a.getAttributeId());
	        	va.setAttribute(a.getAttribute());
	        	va.setValue(a.getValue());
	        	va.setSort(a.getSort());
	        	return va;
	        }).collect(Collectors.toSet());
			item.setAttributes(attributes);
		}
		return item;
	}
	
}
