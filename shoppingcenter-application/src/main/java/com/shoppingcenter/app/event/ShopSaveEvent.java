package com.shoppingcenter.app.event;

import org.springframework.context.ApplicationEvent;

import com.shoppingcenter.domain.shop.Shop;

import lombok.Getter;

@Getter
public class ShopSaveEvent extends ApplicationEvent {

    private Shop shop;

    public ShopSaveEvent(Object source, Shop shop) {
        super(source);
        this.shop = shop;
    }

}
