package com.shoppingcenter.app.controller.subscription.dto;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.PageDataDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlanDTO {

    private long id;

    private String title;

    private int duration;

    private boolean promoUsable;

    private BigDecimal price;

    private long createdAt;

    public static Type pageType() {
        return new TypeToken<PageDataDTO<SubscriptionPlanDTO>>() {
        }.getType();
    }
    
    public static Type listType() {
        return new TypeToken<List<SubscriptionPlanDTO>>() {
        }.getType();
    }
}
