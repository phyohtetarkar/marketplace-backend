package com.shoppingcenter.core.discount.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.data.discount.DiscountEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Discount {

	private long id;

	private String title;

	private double value;

	private DiscountEntity.Type type;

	@JsonProperty(access = Access.WRITE_ONLY)
	private long shopId;

	@JsonProperty(access = Access.READ_ONLY)
	private long createdAt;

	public static Discount create(DiscountEntity entity) {
		Discount d = new Discount();
		d.setId(entity.getId());
		d.setTitle(entity.getTitle());
		d.setValue(entity.getValue());
		d.setType(entity.getType());
		d.setCreatedAt(entity.getCreatedAt());
		return d;
	}
}
