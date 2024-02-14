package com.marketplace.data.discount;

import java.math.BigDecimal;

import com.marketplace.data.AuditingEntity;
import com.marketplace.data.shop.ShopEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.discount.Discount;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity(name = "Discount")
@Table(name = Constants.TABLE_PREFIX + "shop_discount")
public class DiscountEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String title;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal value;

	@Enumerated(EnumType.STRING)
	private Discount.Type type;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;

	public DiscountEntity() {
	}
}
