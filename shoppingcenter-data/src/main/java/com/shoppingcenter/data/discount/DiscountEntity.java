package com.shoppingcenter.data.discount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.shop.ShopEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Discount")
@Table(name = Entities.TABLE_PREFIX + "discount")
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

	@Version
	private long version;

	@ManyToOne(optional = false)
	private ShopEntity shop;

	public DiscountEntity() {
	}
}
