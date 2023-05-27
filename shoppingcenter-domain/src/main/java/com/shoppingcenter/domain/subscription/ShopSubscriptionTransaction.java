package com.shoppingcenter.domain.subscription;

import java.math.BigDecimal;

import com.shoppingcenter.domain.payment.PaymentResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionTransaction {

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

	private long shopSubscriptionId;
	
	public ShopSubscriptionTransaction() {
		this.amount = BigDecimal.valueOf(0);
	}

	public ShopSubscriptionTransaction(PaymentResult result) {
		this.merchantId = result.getMerchantId();
		this.invoiceNo = result.getInvoiceNo();
		this.cardNo = result.getCardNo();
		this.amount = result.getAmount() != null ? new BigDecimal(result.getAmount()) : BigDecimal.valueOf(0);
		this.currencyCode = result.getCurrencyCode();
		this.tranRef = result.getTranRef();
		this.referenceNo = result.getReferenceNo();
		this.agentCode = result.getAgentCode();
		this.channelCode = result.getChannelCode();
		this.approvalCode = result.getApprovalCode();
		this.eci = result.getEci();
		this.transactionDateTime = result.getTransactionDateTime();
		this.respCode = result.getRespCode();
		this.respDesc = result.getRespDesc();
		this.shopSubscriptionId = Long.parseLong(invoiceNo);
	}

}
