package com.shoppingcenter.app.controller.subscription.dto;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;

import com.shoppingcenter.domain.PageData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlanDTO {

    private long id;

    private String title;

    private int duration;

    private boolean promoUsable;

    private double price;

    private long createdAt;

    public static Type pageType() {
        return new TypeToken<PageData<SubscriptionPlanDTO>>() {
        }.getType();
    }
}
