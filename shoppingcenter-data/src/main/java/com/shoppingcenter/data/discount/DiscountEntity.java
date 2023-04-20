package com.shoppingcenter.data.discount;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.discount.Discount;

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

	private double value;

	@Enumerated(EnumType.STRING)
	private Discount.Type type;

	// private String startAt;

	// private String endAt;

	// private boolean yearly;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;

	public DiscountEntity() {
	}
}
