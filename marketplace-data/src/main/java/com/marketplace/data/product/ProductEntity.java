package com.marketplace.data.product;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.data.AuditingEntity;
import com.marketplace.data.category.CategoryEntity;
import com.marketplace.data.discount.DiscountEntity;
import com.marketplace.data.shop.ShopEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.product.Product;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Product")
@Table(name = Constants.TABLE_PREFIX + "product")
public class ProductEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String sku;

	@Column(columnDefinition = "TEXT")
	private String name;

	@Column(columnDefinition = "TEXT", unique = true)
	private String slug;

	@Column(columnDefinition = "TEXT")
	private String brand;

	@Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

	private boolean featured;

	private boolean newArrival;

	private boolean withVariant;

	private boolean deleted;
	
	private boolean available;

	private String thumbnail;

//	@Column(columnDefinition = "TEXT")
	@Lob
    @Basic(fetch = FetchType.LAZY)
	private String description;
	
//	@Column(columnDefinition = "TEXT")
	@Lob
    @Basic(fetch = FetchType.LAZY)
	private String videoEmbed;
	
	@Enumerated(EnumType.STRING)
	private Product.Status status;
	
	@Version
	private long version;

	@ManyToOne
	private DiscountEntity discount;

	@ManyToOne
	private CategoryEntity category;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductImageEntity> images;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductVariantEntity> variants;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductAttributeEntity> attributes;
	

	public ProductEntity() {
		this.price = BigDecimal.ZERO;
		this.status = Product.Status.DRAFT;
	}

}
