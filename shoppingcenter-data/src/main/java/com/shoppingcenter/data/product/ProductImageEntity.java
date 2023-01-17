package com.shoppingcenter.data.product;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.shoppingcenter.data.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopImage")
@Table(name = Entities.TABLE_PREFIX + "product_image")
public class ProductImageEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;

	private boolean thumbnail;

	private long size;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ProductEntity product;

	public ProductImageEntity() {
	}

	@PrePersist
	private void prePersist() {
		this.id = String.format("%d:%d", product.getId(), System.currentTimeMillis());
	}

}
