package com.shoppingcenter.data.shop.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class ShopDeleteEvent extends ApplicationEvent {

    private long shopId;

    public ShopDeleteEvent(Object source, long shopId) {
        super(source);
        this.shopId = shopId;
    }

}
