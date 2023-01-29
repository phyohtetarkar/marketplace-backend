package com.shoppingcenter.data.variant;

import java.io.Serializable;

import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.product.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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

	/**
	 * Combination of Product ID and option/option
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String title;

	private double price;

	private String sku;

	private int stockLeft;

	/**
	 * JSON string as [{ option: 'option', value: 'value' }]
	 */
	@Column(columnDefinition = "TEXT")
	private String options;

	@Version
	private long version;

	// @ElementCollection
	// @CollectionTable(name = Constants.TABLE_PREFIX + "product_variant_option")
	// private Set<ProductVariantOptionData> options;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ProductEntity product;

	public ProductVariantEntity() {
	}

}
