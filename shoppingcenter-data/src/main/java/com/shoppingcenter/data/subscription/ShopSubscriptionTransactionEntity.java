package com.shoppingcenter.data.subscription;

import java.math.BigDecimal;

import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopSubscriptionTransaction")
@Table(name = Constants.TABLE_PREFIX + "shop_subscription_transaction")
public class ShopSubscriptionTransactionEntity {

	@Id
	private long id;

	private String merchantId;

	private String invoiceNo;
	
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
	
	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private ShopSubscriptionEntity shopSubscription;

	public ShopSubscriptionTransactionEntity() {
		this.amount = BigDecimal.valueOf(0);
	}
	
}
