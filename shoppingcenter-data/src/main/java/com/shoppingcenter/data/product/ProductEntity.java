package com.shoppingcenter.data.product;

import java.util.List;
import java.util.Set;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.data.product.variant.ProductVariantEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Product")
@Table(name = Constants.TABLE_PREFIX + "product", indexes = {
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

	private int stockLeft;

	private boolean featured;

	private boolean newArrival;

	private boolean withVariant;

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

	@ElementCollection
	@CollectionTable(name = Constants.TABLE_PREFIX + "product_category_id")
	private Set<Integer> categories;

	@ElementCollection
	@CollectionTable(name = Constants.TABLE_PREFIX + "product_option", joinColumns = {
			@JoinColumn(name = "product_id")
	})
	private Set<ProductOptionEntity> options;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductImageEntity> images;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ProductVariantEntity> variants;

	public ProductEntity() {
	}

}
