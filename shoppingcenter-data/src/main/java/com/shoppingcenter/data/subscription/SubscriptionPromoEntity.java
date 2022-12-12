package com.shoppingcenter.data.subscription;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Constants.TABLE_PREFIX + "subscription_promo")
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
	
	@Enumerated(EnumType.STRING)
	private ValueType type;
	
	private long expiredAt;
	
	public SubscriptionPromoEntity() {
		this.type = ValueType.PERCENTAGE;
	}

}
