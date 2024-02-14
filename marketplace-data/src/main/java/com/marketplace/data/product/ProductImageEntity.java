package com.marketplace.data.product;

import com.marketplace.domain.Constants;

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
@Entity(name = "ProductImage")
@Table(name = Constants.TABLE_PREFIX + "product_image")
public class ProductImageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private boolean thumbnail;

	private long size;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProductEntity product;

	public ProductImageEntity() {
	}
}
