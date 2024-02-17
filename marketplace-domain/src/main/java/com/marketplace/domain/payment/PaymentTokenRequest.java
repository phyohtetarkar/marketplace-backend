package com.marketplace.domain.payment;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTokenRequest {
	
	private long shopId;

	private String invoiceNo;
	
	private String description;
	
	private BigDecimal amount;
	
	private String currencyCode;
	
	public PaymentTokenRequest() {
		this.amount = BigDecimal.valueOf(0.0);
	}
	
}
