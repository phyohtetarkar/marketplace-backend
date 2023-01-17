package com.shoppingcenter.app.controller.discount.dto;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.data.discount.DiscountEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDTO {
	private String id;

	private long shopId;

	private String title;

	private double value;

	private DiscountEntity.Type type;

	private long createdAt;

	public static Type pageType() {
		return new TypeToken<PageData<DiscountDTO>>() {

		}.getType();
	}

}
