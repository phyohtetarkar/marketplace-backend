package com.shoppingcenter.data.product;

import java.util.List;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.variant.ProductVariantEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = Entities.TABLE_PREFIX + "product", indexes = {
// @Index(name = "categoryIndex", columnList = "mainCategoryId, subCategoryId")
})
public class ProductEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

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

	private Double price;

	@Column(columnDefinition = "TEXT")
	private String thumbnail;

	private int stockLeft;

	private boolean featured;

	private boolean newArrival;

	private String status;

	@Column(columnDefinition = "TEXT")
	private String description;

	// private Integer mainCategoryId;

	// private Integer subCategoryId;

	@Version
	private long version;

	@ManyToOne
	private DiscountEntity discount;

	@ManyToOne(optional = false)
	private CategoryEntity category;

	@ManyToOne(optional = false)
	private ShopEntity shop;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductOptionEntity> options;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductImageEntity> images;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductVariantEntity> variants;

	public ProductEntity() {
	}

}
