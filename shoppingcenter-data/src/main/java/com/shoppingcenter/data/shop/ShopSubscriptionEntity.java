package com.shoppingcenter.data.shop;

import java.math.BigDecimal;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.shop.ShopSubscription;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopSubscription")
@Table(name = Constants.TABLE_PREFIX + "shop_subscription")
public class ShopSubscriptionEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = Constants.TABLE_PREFIX + "shop_subscription_id_seq")
	@SequenceGenerator(
			name = Constants.TABLE_PREFIX + "shop_subscription_id_seq", 
			initialValue = 1000, 
			allocationSize = 1)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String title;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal subTotalPrice;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal discount;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	private ShopSubscription.Status status;
	
	private int duration;

	private long startAt;

	private long endAt;
	
	private boolean preSubscription;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;
	
	@OneToOne(mappedBy = "shopSubscription", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private ShopSubscriptionTransactionEntity shopSubscriptionTransaction;
	
	public ShopSubscriptionEntity() {
		this.status = ShopSubscription.Status.PROCESSING;
		this.subTotalPrice = BigDecimal.valueOf(0);
		this.totalPrice = BigDecimal.valueOf(0);
		this.discount = BigDecimal.valueOf(0);
	}

}
