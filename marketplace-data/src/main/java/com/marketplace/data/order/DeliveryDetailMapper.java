package com.marketplace.data.order;

import com.marketplace.domain.order.DeliveryDetail;

public interface DeliveryDetailMapper {

	public static DeliveryDetail toDomain(DeliveryDetailEntity entity) {
		var delivery = new DeliveryDetail();
		delivery.setName(entity.getName());
		delivery.setPhone(entity.getPhone());
		delivery.setAddress(entity.getAddress());
		return delivery;
	}
	
}
