package com.shoppingcenter.data.order;

import com.shoppingcenter.domain.order.PaymentDetail;

public class PaymentDetailMapper {

	public static PaymentDetail toDomain(PaymentDetailEntity entity) {
		var payment = new PaymentDetail();
		payment.setOrderId(entity.getId());
		payment.setAccountType(entity.getAccountType());
		payment.setReceiptImage(entity.getReceiptImage());
		return payment;
	}
	
}
