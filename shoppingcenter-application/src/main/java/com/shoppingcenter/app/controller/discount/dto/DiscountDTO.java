package com.shoppingcenter.app.controller.discount.dto;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.discount.model.Discount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDTO {
	private long id;

	private long shopId;

	private String title;

	private double value;

	private Discount.Type type;

	private long createdAt;

	public static Type pageType() {
		return new TypeToken<PageData<DiscountDTO>>() {

		}.getType();
	}

}
