package com.shoppingcenter.data.product;

import java.math.BigDecimal;
import java.util.List;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.product.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	private int stockLeft;

	private boolean featured;

	private boolean newArrival;

	private boolean withVariant;

	private boolean disabled;

	private String thumbnail;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(columnDefinition = "TEXT")
	private String videoUrl;
	
	@Enumerated(EnumType.STRING)
	private Product.Status status;
	
	@Version
	private long version;

	@ManyToOne
	private DiscountEntity discount;

	@ManyToOne(fetch = FetchType.LAZY)
	private CategoryEntity category;

	@ManyToOne
	private ShopEntity shop;

//	@ElementCollection
//	@CollectionTable(name = Constants.TABLE_PREFIX + "product_option", joinColumns = {
//			@JoinColumn(name = "product_id")
//	})
//	private Set<ProductOptionEntity> options;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductImageEntity> images;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductVariantEntity> variants;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductAttributeEntity> attributes;
	

	public ProductEntity() {
		this.price = new BigDecimal("0");
	}

}
