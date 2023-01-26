package com.shoppingcenter.data.subscription;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "SubscriptionPromo")
@Table(name = Entities.TABLE_PREFIX + "subscription_promo")
public class SubscriptionPromoEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	public enum ValueType {
		PERCENTAGE, FIXED_AMOUNT
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private String code;

	private double value;

	private double maxConstraint;

	private String type;

	private long expiredAt;

	/**
	 * If true, this promo code can be used by all user.
	 */
	private boolean share;

	private boolean used;

	public SubscriptionPromoEntity() {
	}

}
