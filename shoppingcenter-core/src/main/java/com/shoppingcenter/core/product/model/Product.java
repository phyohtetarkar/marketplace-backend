package com.shoppingcenter.core.product.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.shoppingcenter.core.category.model.Category;
import com.shoppingcenter.core.discount.model.Discount;
import com.shoppingcenter.core.shop.model.Shop;
import com.shoppingcenter.data.product.ProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	private long id;

	private String sku;

	private String name;

	private String slug;

	private String brand;

	private Double price;

	private boolean outOfStock;

	private boolean featured;

	private String thumbnail;

	private String description;

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

	public static Product create(ProductEntity entity, String baseUrl) {
		String imageBaseUrl = imageBaseUrl(entity, baseUrl);
		Product p = createCompat(entity, imageBaseUrl);
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
		p.setOutOfStock(entity.isOutOfStock());
		p.setCategory(Category.createCompat(entity.getCategory(), baseUrl));
		p.setDiscount(Discount.create(entity.getDiscount()));
		p.setShop(Shop.createCompat(entity.getShop(), baseUrl));
		p.setCreatedAt(entity.getCreatedAt());
		if (StringUtils.hasText(entity.getThumbnail())) {
			p.setThumbnail(imageBaseUrl + entity.getThumbnail());
		}
		return p;
	}

	private static String imageBaseUrl(ProductEntity entity, String baseUrl) {
		return String.format("%s/%s/%d/", baseUrl, "product/", entity.getId());
	}

}
