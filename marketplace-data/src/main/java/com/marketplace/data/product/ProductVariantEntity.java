package com.marketplace.data.product;

import java.math.BigDecimal;
import java.util.Set;

import com.marketplace.domain.Constants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProductVariant")
@Table(name = Constants.TABLE_PREFIX + "product_variant")
public class ProductVariantEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(precision = 12, scale = 2, nullable = false)
	private BigDecimal price;

	private String sku;
	
	private boolean deleted;
	
	private boolean available;
	
	@Version
	private long version;

	@OneToMany(mappedBy = "variant", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<ProductVariantAttributeEntity> attributes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductEntity product;

	public ProductVariantEntity() {
		this.price = new BigDecimal("0");
	}

}
