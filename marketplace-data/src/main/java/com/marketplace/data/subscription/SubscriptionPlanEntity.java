package com.marketplace.data.subscription;

import java.math.BigDecimal;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;

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
@Entity(name = "SubscriptionPlan")
@Table(name = Constants.TABLE_PREFIX + "subscription_plan")
public class SubscriptionPlanEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String title;

	private int duration; // by days

	private boolean promoUsable;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal price;

	public SubscriptionPlanEntity() {
		this.price = BigDecimal.valueOf(0);
	}

}
