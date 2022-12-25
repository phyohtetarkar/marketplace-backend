package com.shoppingcenter.data.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Utils;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.variant.ProductVariantEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Utils.TABLE_PREFIX + "product", indexes = {
		@Index(name = "categoryIndex", columnList = "mainCategoryId, subCategoryId")
})
public class ProductEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	public enum Status {
		DRAFT, PUBLISHED, ARCHIVED, DENIED
	}

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

	private boolean outOfStock;

	private boolean featured;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(columnDefinition = "TEXT")
	private String description;

	private boolean hidden;

	private Integer mainCategoryId;

	private Integer subCategoryId;

	@ManyToOne
	private DiscountEntity discount;

	@ManyToOne
	private CategoryEntity category;

	@ManyToOne
	private ShopEntity shop;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	private List<ProductOptionEntity> options;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	private List<ProductImageEntity> images;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	private List<ProductVariantEntity> variants;

	public ProductEntity() {
		this.status = Status.DRAFT;
	}

}
