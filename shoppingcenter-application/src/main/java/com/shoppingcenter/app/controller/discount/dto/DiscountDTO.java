package com.shoppingcenter.app.controller.discount.dto;

import java.lang.reflect.Type;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.data.discount.DiscountEntity;
import org.modelmapper.TypeToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDTO {
	private long shopId;

	private String issuedAt;

	private String title;

	private double value;

	private DiscountEntity.Type type;

	private long createdAt;

	@Getter
	@Setter
	public static class ID {
		private long shopId;

		private String issuedAt;
	}

	public static Type pageType() {
		return new TypeToken<PageData<DiscountDTO>>() {

		}.getType();
	}
}
