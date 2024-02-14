package com.marketplace.data.shop;

import com.marketplace.domain.shop.ShopAcceptedPayment;

public interface ShopAcceptedPaymentMapper {

	static ShopAcceptedPayment toDomain(ShopAcceptedPaymentEntity entity) {
		var payment = new ShopAcceptedPayment();
		payment.setId(entity.getId());
		payment.setAccountName(entity.getAccountName());
		payment.setAccountType(entity.getAccountType());
		payment.setAccountNumber(entity.getAccountNumber());
		return payment;
	}
	
}
