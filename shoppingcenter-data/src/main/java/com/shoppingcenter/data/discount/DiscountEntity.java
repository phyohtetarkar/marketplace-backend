package com.shoppingcenter.data.discount;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Discount")
@IdClass(DiscountEntity.ID.class)
@Table(name = Entities.TABLE_PREFIX + "discount")
public class DiscountEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	public enum Type {
		PERCENTAGE, FIXED_AMOUNT
	}

	@Id
	private long shopId;

	@Id
	private String issuedAt;

	@Column(columnDefinition = "TEXT")
	private String title;

	private double value;

	@Enumerated(EnumType.STRING)
	private Type type;

	@Version
	private long version;

	public DiscountEntity() {
		this.type = Type.PERCENTAGE;
	}

	@PrePersist
	private void prePersist() {
		this.issuedAt = Instant.now().toString();
	}

	@Getter
	@Setter
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		private long shopId;

		private String issuedAt;

	}
}
