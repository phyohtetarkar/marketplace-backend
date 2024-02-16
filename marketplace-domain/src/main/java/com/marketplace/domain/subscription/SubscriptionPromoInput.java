package com.marketplace.domain.subscription;

import java.math.BigDecimal;

import com.marketplace.domain.subscription.SubscriptionPromo.ValueType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPromoInput {
	
	private long id;

	private String code;

	private BigDecimal value;

	private BigDecimal minConstraint;

	private ValueType valueType;

	private long expiredAt;
	
}
