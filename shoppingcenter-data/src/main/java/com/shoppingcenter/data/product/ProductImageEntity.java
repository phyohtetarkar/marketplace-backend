package com.shoppingcenter.data.product;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.shoppingcenter.data.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopImage")
@IdClass(ProductImageEntity.ID.class)
@Table(name = Entities.TABLE_PREFIX + "product_image")
public class ProductImageEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long productId;

	@Id
	private String createdAt;

	private String name;

	private boolean thumbnail;

	private long size;

	public ProductImageEntity() {
	}

	@PrePersist
	private void prePersist() {
		this.createdAt = Instant.now().toString();
	}

	@Getter
	@Setter
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		private long productId;

		private String createdAt;

	}

}
