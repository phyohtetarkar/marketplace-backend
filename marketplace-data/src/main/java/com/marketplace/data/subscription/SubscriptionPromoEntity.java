package com.marketplace.data.subscription;

import java.math.BigDecimal;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.subscription.SubscriptionPromo;
import com.marketplace.domain.subscription.SubscriptionPromo.ValueType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "SubscriptionPromo")
@Table(name = Constants.TABLE_PREFIX + "subscription_promo")
public class SubscriptionPromoEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private String code;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal value;

	@Column(precision = 10, scale = 2)
	private BigDecimal minConstraint;

	@Enumerated(EnumType.STRING)
	private SubscriptionPromo.ValueType valueType;

	private long expiredAt;

	private boolean used;
	
	@Version
	private long version;

	public SubscriptionPromoEntity() {
		this.value = BigDecimal.ZERO;
		this.valueType = ValueType.FIXED_AMOUNT;
	}

}
