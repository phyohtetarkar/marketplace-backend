package com.marketplace.domain.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResult {

	private String merchantId;

	private String invoiceNo;
	
	private String cardNo;

	private Double amount;

	private String currencyCode;

	private String tranRef;

	private String referenceNo;
	
	private String agentCode;
	
	private String channelCode;

	private String approvalCode;

	private String eci;

	private String transactionDateTime;

	private String respCode;

	private String respDesc;

	public PaymentResult() {
	}
}
