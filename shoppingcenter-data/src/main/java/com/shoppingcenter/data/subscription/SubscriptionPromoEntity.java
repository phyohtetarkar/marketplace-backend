package com.shoppingcenter.data.subscription;

import java.math.BigDecimal;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "SubscriptionPromo")
@Table(name = Constants.TABLE_PREFIX + "subscription_promo")
public class SubscriptionPromoEntity extends AuditingEntity {

	public enum ValueType {
		PERCENTAGE, FIXED_AMOUNT
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private String code;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal value;

	@Column(precision = 10, scale = 2)
	private BigDecimal minConstraint;

	private String valueType;

	private long expiredAt;

	private boolean used;

	private long usedCount;

	public SubscriptionPromoEntity() {
		this.value = new BigDecimal(0);
	}

}
