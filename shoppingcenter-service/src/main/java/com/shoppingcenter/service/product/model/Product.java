package com.shoppingcenter.service.product.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.service.category.model.Category;
import com.shoppingcenter.service.discount.model.Discount;
import com.shoppingcenter.service.shop.model.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	public enum Status {
		DRAFT, PUBLISHED, ARCHIVED, DENIED
	}

	private long id;

	private String sku;

	private String name;

	private String slug;

	private String brand;

	private String priceRange;

	private Double price;

	private int stockLeft;

	private boolean featured;

	private boolean newArrival;

	private boolean withVariant;

	private String thumbnail;

	private String description;

	private Status status;

	private List<ProductOption> options;

	private List<ProductVariant> variants;

	private List<ProductImage> images;

	private Discount discount;

	private Category category;

	private Shop shop;

	private long createdAt;

	private Long discountId;

	private int categoryId;

	private long shopId;

	public Product() {
		this.status = Status.DRAFT;
	}

	public static Product create(ProductEntity entity, String baseUrl) {
		String imageBaseUrl = imageBaseUrl(entity, baseUrl);
		Product p = createCompat(entity, baseUrl);
		p.setDescription(entity.getDescription());
		p.setImages(entity.getImages().stream().map(e -> ProductImage.create(e, imageBaseUrl))
				.collect(Collectors.toList()));
		p.setOptions(entity.getOptions().stream().map(ProductOption::create).collect(Collectors.toList()));
		return p;
	}

	public static Product createCompat(ProductEntity entity, String baseUrl) {
		String imageBaseUrl = imageBaseUrl(entity, baseUrl);
		Product p = new Product();
		p.setId(entity.getId());
		p.setName(entity.getName());
		p.setSlug(entity.getSlug());
		p.setBrand(entity.getBrand());
		p.setPrice(entity.getPrice());
		p.setStockLeft(entity.getStockLeft());
		p.setFeatured(entity.isFeatured());
		p.setNewArrival(entity.isNewArrival());
		p.setCategory(Category.createCompat(entity.getCategory(), baseUrl));
		p.setShop(Shop.createCompat(entity.getShop(), baseUrl));
		p.setCreatedAt(entity.getCreatedAt());
		p.setStatus(Status.valueOf(entity.getStatus()));
		p.setWithVariant(entity.isWithVariant());
		if (StringUtils.hasText(entity.getThumbnail())) {
			p.setThumbnail(imageBaseUrl + entity.getThumbnail());
		}
		if (p.getDiscount() != null) {
			p.setDiscount(Discount.create(entity.getDiscount()));
		}
		return p;
	}

	private static String imageBaseUrl(ProductEntity entity, String baseUrl) {
		return String.format("%s%s/%d/", baseUrl, "product", entity.getShop().getId());
	}

}
