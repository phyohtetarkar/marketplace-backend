package com.marketplace.data.order;

import com.marketplace.domain.order.PaymentDetail;

public class PaymentDetailMapper {

	public static PaymentDetail toDomain(PaymentDetailEntity entity) {
		var payment = new PaymentDetail();
		payment.setAccountType(entity.getAccountType());
		payment.setReceiptImage(entity.getReceiptImage());
		return payment;
	}
	
}
