package com.shoppingcenter.core.discount.model;

import com.shoppingcenter.data.discount.DiscountEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Discount {

	private String id;

	private long shopId;

	private String title;

	private double value;

	private DiscountEntity.Type type;

	private long createdAt;

	public static Discount create(DiscountEntity entity) {
		Discount d = new Discount();
		d.setId(entity.getId());
		d.setShopId(entity.getShop().getId());
		d.setTitle(entity.getTitle());
		d.setValue(entity.getValue());
		d.setType(entity.getType());
		d.setCreatedAt(entity.getCreatedAt());
		return d;
	}

	@Getter
	@Setter
	public static class ID {
		private long shopId;

		private String issuedAt;
	}

}
