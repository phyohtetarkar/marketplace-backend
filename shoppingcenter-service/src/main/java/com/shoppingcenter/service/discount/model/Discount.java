package com.shoppingcenter.service.discount.model;

import com.shoppingcenter.data.discount.DiscountEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Discount {

	public enum Type {
		PERCENTAGE, FIXED_AMOUNT
	}

	private long id;

	private long shopId;

	private String title;

	private double value;

	private Type type;

	private Long totalProduct;

	private Long createdAt;

	public Discount() {
		this.type = Type.PERCENTAGE;
	}

	public static Discount create(DiscountEntity entity) {
		Discount d = new Discount();
		d.setId(entity.getId());
		d.setShopId(entity.getShop().getId());
		d.setTitle(entity.getTitle());
		d.setValue(entity.getValue());
		d.setType(Type.valueOf(entity.getType()));
		d.setCreatedAt(entity.getCreatedAt());
		return d;
	}

}
