package com.marketplace.domain.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTokenResponse {

	private String webPaymentUrl;

	private String paymentToken;

	private String respCode;

	private String respDesc;

	@Override
	public String toString() {
		return "PaymentTokenResponse [webPaymentUrl=" + webPaymentUrl + ", paymentToken=" + paymentToken + ", respCode="
				+ respCode + ", respDesc=" + respDesc + "]";
	}

}
