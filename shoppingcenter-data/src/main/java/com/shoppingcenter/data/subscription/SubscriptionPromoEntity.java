package com.shoppingcenter.data.subscription;

import java.math.BigDecimal;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.subscription.SubscriptionPromo;
import com.shoppingcenter.domain.subscription.SubscriptionPromo.ValueType;

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
