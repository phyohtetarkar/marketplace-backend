package com.shoppingcenter.data.product;

import java.math.BigDecimal;
import java.util.Set;

import com.shoppingcenter.domain.Constants;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProductVariant")
@Table(name = Constants.TABLE_PREFIX + "product_variant")
public class ProductVariantEntity {


	// @GeneratedValue(generator = "UUID")
	// @GenericGenerator(
	// name = "UUID",
	// strategy = "org.hibernate.id.UUIDGenerator"
	// )
	// @Column(name = "id", updatable = false, nullable = false)

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(precision = 12, scale = 2, nullable = false)
	private BigDecimal price;

	private String sku;

	private int stockLeft;
	
	@Version
	private long version;

	@ElementCollection
	@CollectionTable(name = Constants.TABLE_PREFIX + "product_variant_attribute", joinColumns = {
			@JoinColumn(name = "variant_id")
	})
	private Set<ProductVariantAttributeEntity> attributes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductEntity product;

	public ProductVariantEntity() {
		this.price = new BigDecimal("0");
	}

}
