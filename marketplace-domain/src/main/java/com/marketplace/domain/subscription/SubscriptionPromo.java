package com.marketplace.domain.subscription;

import java.math.BigDecimal;

import com.marketplace.domain.Audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPromo {
	
	public enum ValueType {
		PERCENTAGE, FIXED_AMOUNT
	}

	private long id;

	private String code;

	private BigDecimal value;

	private BigDecimal minConstraint;

	private ValueType valueType;

	private long expiredAt;

	private boolean used;
	
	private Audit audit = new Audit();
	
	public SubscriptionPromo() {
		this.value = BigDecimal.ZERO;
		this.valueType = ValueType.FIXED_AMOUNT;
	}
	
	
}
