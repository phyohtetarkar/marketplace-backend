package com.shoppingcenter.core.product.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.core.category.model.Category;
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

	@JsonProperty(access = Access.READ_ONLY)
	private String thumbnail;

	private String description;

	private List<ProductOption> options;

	private List<ProductVariant> variants;

	private List<ProductImage> images;

	private Discount discount;

	private Category category;

	private Shop shop;

	public static Product create(ProductEntity entity, String baseUrl) {
		String imageBaseUrl = createImageBaseUrl(entity, baseUrl);
		Product p = new Product();
		return p;
	}

	public static Product createMinimal(ProductEntity entity, String baseUrl) {
		String imageBaseUrl = createImageBaseUrl(entity, baseUrl);
		Product p = new Product();
		return p;
	}

	private static String createImageBaseUrl(ProductEntity entity, String baseUrl) {
		return String.format("%s/%s/%s/%s/", baseUrl, "shops/", entity.getShop().getSlug(), entity.getSlug());
	}

}
