package com.shoppingcenter.data.order;

import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.data.user.UserMapper;
import com.shoppingcenter.domain.order.Order;

public class OrderMapper {
	
	public static Order toDomain(OrderEntity entity) {
		var order = toDomainCompat(entity);
		order.setItems(entity.getItems().stream().map(OrderItemMapper::toDomainCompat).toList());
		order.setUser(UserMapper.toDomain(entity.getUser()));
		order.setShop(ShopMapper.toDomainCompat(entity.getShop()));
		order.setDelivery(DeliveryDetailMapper.toDomain(entity.getDelivery()));
		if (entity.getPayment() != null) {
			order.setPayment(PaymentDetailMapper.toDomain(entity.getPayment()));
		}
		return order;
	}

	public static Order toDomainCompat(OrderEntity entity) {
		var order = new Order();
		order.setId(entity.getId());
		order.setOrderCode(entity.getOrderCode());
		order.setSubTotalPrice(entity.getSubTotalPrice());
		order.setTotalPrice(entity.getTotalPrice());
		order.setQuantity(entity.getQuantity());
		order.setDiscount(entity.getDiscount());
		order.setNote(entity.getNote());
		order.setStatus(entity.getStatus());
		order.setPaymentMethod(entity.getPaymentMethod());
		order.setCreatedAt(entity.getCreatedAt());
		return order;
	}
	
}
