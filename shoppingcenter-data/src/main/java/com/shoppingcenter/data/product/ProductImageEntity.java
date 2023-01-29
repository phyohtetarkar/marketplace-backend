package com.shoppingcenter.data.product;

import java.io.Serializable;

import com.shoppingcenter.data.Entities;

import jakarta.persistence.Entity;
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
@Entity(name = "ShopImage")
@Table(name = Entities.TABLE_PREFIX + "product_image")
public class ProductImageEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private boolean thumbnail;

	private long size;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ProductEntity product;

	public ProductImageEntity() {
	}

}
