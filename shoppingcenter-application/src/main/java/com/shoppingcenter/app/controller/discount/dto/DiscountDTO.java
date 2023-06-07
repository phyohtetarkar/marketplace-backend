package com.shoppingcenter.app.controller.discount.dto;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

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

	private BigDecimal value;

	private Discount.Type type;

	private Long totalProduct;

	private Long createdAt;
	
	public static Type listType() {
        return new TypeToken<List<DiscountDTO>>() {
        }.getType();
    }

	public static Type pageType() {
		return new TypeToken<PageDataDTO<DiscountDTO>>() {
		}.getType();
	}

}
