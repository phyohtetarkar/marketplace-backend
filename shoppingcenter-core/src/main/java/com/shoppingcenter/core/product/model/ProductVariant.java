package com.shoppingcenter.core.product.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.data.variant.ProductVariantEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariant {

	private long id;

	private String title;

	private String sku;

	private boolean outOfStock;

	private List<ProductVariantOption> options;

	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean deleted;

	public static ProductVariant create(ProductVariantEntity entity) {
		ProductVariant pv = new ProductVariant();
		pv.setId(entity.getId());
		pv.setTitle(entity.getTitle());
		pv.setSku(entity.getSku());
		pv.setOutOfStock(entity.isOutOfStock());
		pv.setOptions(entity.getOptions().stream().map(op -> new ProductVariantOption(op.getOption(), op.getValue()))
				.collect(Collectors.toList()));
		return pv;
	}
}
