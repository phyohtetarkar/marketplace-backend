package com.shoppingcenter.app.controller.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.domain.payment.PaymentGatewayAdapter;
import com.shoppingcenter.domain.subscription.usecase.CompleteShopSubscriptionUseCase;

@Service
public class PaymentService {

	@Autowired
	private CompleteShopSubscriptionUseCase completeShopSubscriptionUseCase;
	
	@Autowired
	private PaymentGatewayAdapter paymentGatewayAdapter;
	
	@Transactional
	public void handlePaymentResult(String payload) {
		var result = paymentGatewayAdapter.decodeResultPayload(payload);
		completeShopSubscriptionUseCase.apply(result);
	}
	
}
