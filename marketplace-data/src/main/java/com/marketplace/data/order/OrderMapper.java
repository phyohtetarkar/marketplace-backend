package com.marketplace.data.order;

import com.marketplace.data.AuditMapper;
import com.marketplace.data.shop.ShopMapper;
import com.marketplace.data.user.UserMapper;
import com.marketplace.domain.order.Order;

public class OrderMapper {
	
	public static Order toDomain(OrderEntity entity) {
		var order = toDomainCompat(entity);
		order.setCustomer(UserMapper.toDomain(entity.getCustomer()));
		order.setShop(ShopMapper.toDomainCompat(entity.getShop()));
		order.setItems(entity.getItems().stream().map(OrderItemMapper::toDomain).toList());
		if (entity.getDelivery() != null) {
			order.setDelivery(DeliveryDetailMapper.toDomain(entity.getDelivery()));
		}
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
		order.setDiscountPrice(entity.getDiscountPrice());
		order.setNote(entity.getNote());
		order.setStatus(entity.getStatus());
		order.setPaymentMethod(entity.getPaymentMethod());
		order.setAudit(AuditMapper.from(entity));
		return order;
	}
	
}
