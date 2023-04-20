package com.shoppingcenter.app.controller.discount.dto;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.domain.discount.Discount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDTO {
	private long id;

	private String title;

	private double value;

	private Discount.Type type;

	private Long totalProduct;

	private Long createdAt;

	public static Type pageType() {
		return new TypeToken<PageDataDTO<DiscountDTO>>() {

		}.getType();
	}

}
