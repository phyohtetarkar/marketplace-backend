package com.shoppingcenter.data.variant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.product.ProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProductVariant")
@Table(name = Entities.TABLE_PREFIX + "product_variant")
public class ProductVariantEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	// @GeneratedValue(generator = "UUID")
	// @GenericGenerator(
	// name = "UUID",
	// strategy = "org.hibernate.id.UUIDGenerator"
	// )
	// @Column(name = "id", updatable = false, nullable = false)
	@Id
	private String id; // productId:option/option**

	@Column(columnDefinition = "TEXT")
	private String title;

	private double price;

	private String sku;

	private boolean outOfStock;

	@Column(columnDefinition = "TEXT")
	private String options; // [{option: <option>, value: <value>}]

	@Version
	private long version;

	// @ElementCollection
	// @CollectionTable(name = Constants.TABLE_PREFIX + "product_variant_option")
	// private Set<ProductVariantOptionData> options;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ProductEntity product;

	@Transient
	private String optionPath;

	public ProductVariantEntity() {
	}

	@PrePersist
	private void prePersist() {
		this.id = String.format("%d:%s", product.getId(), optionPath);
	}

}
