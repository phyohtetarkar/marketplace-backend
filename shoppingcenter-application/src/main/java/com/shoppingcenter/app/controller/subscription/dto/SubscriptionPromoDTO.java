package com.shoppingcenter.app.controller.subscription.dto;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.domain.subscription.SubscriptionPromo.ValueType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPromoDTO {

	private long id;

	private String code;

	private BigDecimal value;

	private BigDecimal minConstraint;

	private ValueType valueType;

	private long expiredAt;

	private boolean used;
	
	private Long createdAt;
	
	public static Type pageType() {
        return new TypeToken<PageDataDTO<SubscriptionPromoDTO>>() {
        }.getType();
    }
	
}
