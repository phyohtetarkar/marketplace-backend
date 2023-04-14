package com.shoppingcenter.data.discount;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = Constants.TABLE_PREFIX + "discount")
public class DiscountEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String title;

	private double value;

	private String type;

	// private String startAt;

	// private String endAt;

	// private boolean yearly;

	@ManyToOne(optional = false)
	private ShopEntity shop;

	public DiscountEntity() {
	}
}
