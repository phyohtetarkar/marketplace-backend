package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopSubscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionDTO {

	private long id;

	private String title;

	private BigDecimal subTotalPrice;

	private BigDecimal discount;

	private BigDecimal totalPrice;

	private ShopSubscription.Status status;

	private int duration;

	private long startAt;

	private long endAt;

	private boolean preSubscription;

	private Shop shop;

	private long createdAt;
	
	public static Type listType() {
        return new TypeToken<List<ShopSubscriptionDTO>>() {
        }.getType();
    }

}
