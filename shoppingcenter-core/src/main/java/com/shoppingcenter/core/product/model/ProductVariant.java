package com.shoppingcenter.core.product.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.data.variant.ProductVariantEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariant {

	private String id;

	private String title;

	private String sku;

	private double price;

	private boolean outOfStock;

	private List<ProductVariantOption> options;

	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean deleted;

	public ProductVariant() {
		this.options = new ArrayList<>();
	}

	public static ProductVariant create(ProductVariantEntity entity, ObjectMapper mapper) {
		ProductVariant pv = new ProductVariant();
		pv.setId(entity.getId());
		pv.setTitle(entity.getTitle());
		pv.setSku(entity.getSku());
		pv.setPrice(entity.getPrice());
		pv.setOutOfStock(entity.isOutOfStock());
		if (StringUtils.hasText(entity.getOptions())) {
			try {
				pv.setOptions(mapper.readValue(entity.getOptions(), new TypeReference<List<ProductVariantOption>>() {
				}));
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return pv;
	}

	@JsonIgnore
	public String getOptionPath() {
		return options.stream().map(op -> op.getValue()).collect(Collectors.joining("/"));
	}
}
