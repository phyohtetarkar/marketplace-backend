package com.shoppingcenter.data.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Constants.TABLE_PREFIX + "shop_subscription")
public class ShopSubscriptionEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;
	
	public enum Status {
		ACTIVE, PENDING, EXPIRED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(columnDefinition = "TEXT")
	private String planTitle;
	
	private double planCost;
	
	private double discount;
	
	private double taxAmount;
	
	private double totalPrice;
	
	private int planDuration;
	
	private long startAt;
	
	private long endAt;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private long subscriptionPlanId;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;
	
	public ShopSubscriptionEntity() {
		this.status = Status.PENDING;
	}
	
	@PrePersist
	private void prePersist() {
		this.totalPrice = (planCost - discount) + taxAmount;
	}
}
