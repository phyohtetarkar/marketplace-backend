package com.marketplace.domain.subscription;

import java.math.BigDecimal;

import com.marketplace.domain.payment.PaymentResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionTransaction {

	private long invoiceNo;
	
	private String merchantId;

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
	
	public ShopSubscriptionTransaction() {
		this.amount = BigDecimal.ZERO;
	}

	public ShopSubscriptionTransaction(PaymentResult result) {
		this.merchantId = result.getMerchantId();
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
		this.invoiceNo = Long.parseLong(result.getInvoiceNo());
	}

}
