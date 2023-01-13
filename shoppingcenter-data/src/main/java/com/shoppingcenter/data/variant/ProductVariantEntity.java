package com.shoppingcenter.data.variant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Version;

import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.product.ProductEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProductVariant")
@IdClass(ProductVariantEntity.ID.class)
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
	private long productId;

	@Id
	private String optionPath; // option/option**

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

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private ProductEntity product;

	public ProductVariantEntity() {
	}

	@Getter
	@Setter
	@EqualsAndHashCode
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "product_id")
		private long productId;

		@Column(name = "option_path")
		private String optionPath;

		public ID() {

		}

		public ID(long productId, String optionPath) {
			this.productId = productId;
			this.optionPath = optionPath;
		}

	}

}
