package com.shoppingcenter.data.discount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
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

	public enum Type {
		PERCENTAGE, FIXED_AMOUNT
	}

	@Id
	private String id;

	@Column(columnDefinition = "TEXT")
	private String title;

	private double value;

	@Enumerated(EnumType.STRING)
	private Type type;

	@Version
	private long version;

	@ManyToOne(optional = false)
	private ShopEntity shop;

	public DiscountEntity() {
		this.type = Type.PERCENTAGE;
	}

	@PrePersist
	private void prePersist() {
		this.id = String.format("%d:%d", shop.getId(), System.currentTimeMillis());
	}
}
