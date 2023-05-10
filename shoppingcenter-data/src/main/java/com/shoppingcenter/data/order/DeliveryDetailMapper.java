package com.shoppingcenter.data.order;

import com.shoppingcenter.domain.order.DeliveryDetail;

public class DeliveryDetailMapper {

	public static DeliveryDetail toDomain(DeliveryDetailEntity entity) {
		var delivery = new DeliveryDetail();
		delivery.setOrderId(entity.getId());
		delivery.setName(entity.getName());
		delivery.setPhone(entity.getPhone());
		delivery.setCity(entity.getCity());	
		delivery.setAddress(entity.getAddress());
		return delivery;
	}
	
}
