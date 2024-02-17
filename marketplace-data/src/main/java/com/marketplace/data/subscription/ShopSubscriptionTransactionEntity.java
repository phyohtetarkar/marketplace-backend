package com.marketplace.data.subscription;

import java.math.BigDecimal;

import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopSubscriptionTransaction")
@Table(name = Constants.TABLE_PREFIX + "shop_subscription_transaction")
public class ShopSubscriptionTransactionEntity {

	@Id
	private long invoiceNo;

	private String merchantId;
	
	private String cardNo;

	@Column(precision = 12, scale = 5, nullable = false)
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

	public ShopSubscriptionTransactionEntity() {
		this.amount = BigDecimal.valueOf(0);
	}
	
}
