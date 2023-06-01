package com.shoppingcenter.app.controller.shop.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionTransactionDTO {

	private long id;
	
	private String merchantId;

	private String invoiceNo;
	
	private String cardNo;
	
	private BigDecimal amount;

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
	
}
