package com.shoppingcenter.data.product;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.shoppingcenter.data.Entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProductOption")
@IdClass(ProductOptionEntity.ID.class)
@Table(name = Entities.TABLE_PREFIX + "product_option")
public class ProductOptionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long productId;

	@Id
	private String createdAt;

	private String name;

	private int position;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private ProductEntity product;

	public ProductOptionEntity() {
	}

	@PrePersist
	private void prePersist() {
		this.createdAt = Instant.now().toString();
	}

	@Getter
	@Setter
	@EqualsAndHashCode
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "product_id")
		private long productId;

		private String createdAt;

	}

}
