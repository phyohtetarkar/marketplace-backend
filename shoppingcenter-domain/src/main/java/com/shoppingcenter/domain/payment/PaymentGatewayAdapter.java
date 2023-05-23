package com.shoppingcenter.domain.payment;

public interface PaymentGatewayAdapter {

	PaymentTokenResponse requestPaymentToken(PaymentTokenRequest request);
	
	PaymentResult decodeResultPayload(String payload);
	
}
