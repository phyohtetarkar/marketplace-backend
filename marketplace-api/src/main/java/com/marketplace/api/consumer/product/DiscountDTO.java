package com.marketplace.api.consumer.product;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.TypeToken;

import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.discount.Discount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDTO {
	private long id;

	private String title;

	private BigDecimal value;

	private Discount.Type type;
	
	public static Type listType() {
        return new TypeToken<List<DiscountDTO>>() {
        }.getType();
    }

	public static Type pageType() {
		return new TypeToken<PageDataDTO<DiscountDTO>>() {
		}.getType();
	}

}
