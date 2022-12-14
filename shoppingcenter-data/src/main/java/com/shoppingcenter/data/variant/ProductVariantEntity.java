package com.shoppingcenter.data.variant;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shoppingcenter.data.Constants;
import com.shoppingcenter.data.product.ProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Constants.TABLE_PREFIX + "product_variant")
public class ProductVariantEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	// @GeneratedValue(generator = "UUID")
	// @GenericGenerator(
	// name = "UUID",
	// strategy = "org.hibernate.id.UUIDGenerator"
	// )
	// @Column(name = "id", updatable = false, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String title; // option/option**

	private double price;

	private String sku;

	private boolean outOfStock;

	@ElementCollection
	@CollectionTable(name = Constants.TABLE_PREFIX + "product_variant_option")
	private Set<ProductVariantOptionData> options;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProductEntity product;

	public ProductVariantEntity() {
	}

}
