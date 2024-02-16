package com.marketplace.data.subscription;

import java.math.BigDecimal;

import com.marketplace.data.AuditingEntity;
import com.marketplace.data.shop.ShopEntity;
import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopSubscriptionDraft")
@Table(name = Constants.TABLE_PREFIX + "shop_subscription_draft")
public class ShopSubscriptionDraftEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = Constants.TABLE_PREFIX + "shop_subscription_draft_id_seq")
	@SequenceGenerator(
			name = Constants.TABLE_PREFIX + "shop_subscription_draft_id_seq", 
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
	
	private int duration;
	
	private String promoCode;

	@ManyToOne
	private ShopEntity shop;
	
	public ShopSubscriptionDraftEntity() {
		this.subTotalPrice = BigDecimal.ZERO;
		this.totalPrice = BigDecimal.ZERO;
		this.discount = BigDecimal.ZERO;
	}
}
