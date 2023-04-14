package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopSubscription")
@Table(name = Constants.TABLE_PREFIX + "shop_subscription")
public class ShopSubscriptionEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String planTitle;

	private double subTotalPrice;

	private double discount;

	private double totalPrice;

	private int duration;

	private long startAt;

	private long endAt;

	private String status;

	private long subscirptionPlanId;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;

	public ShopSubscriptionEntity() {
	}

}
