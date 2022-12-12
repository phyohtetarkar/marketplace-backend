package com.shoppingcenter.core.product.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.data.product.ProductVariantEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariant {

	private long id;

	private String title;

	private String sku;

	private boolean available;

	private List<ProductVariantOption> options;

	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean deleted;

	public static ProductVariant create(ProductVariantEntity entity) {
		return null;
	}
}
