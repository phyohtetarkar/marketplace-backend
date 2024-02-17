package com.marketplace.domain.payment;

public interface TCTPPaymentGatewayAdapter {

	PaymentTokenResponse requestPaymentToken(PaymentTokenRequest request);
	
	PaymentResult decodeResultPayload(String payload);
	
}
