package com.shoppingcenter.data.subscription;

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

	private double price;

	public SubscriptionPlanEntity() {
	}

}
