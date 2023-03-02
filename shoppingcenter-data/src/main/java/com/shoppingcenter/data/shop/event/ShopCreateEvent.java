package com.shoppingcenter.data.shop.event;

import org.springframework.context.ApplicationEvent;

import com.shoppingcenter.domain.shop.Shop;

import lombok.Getter;

@Getter
public class ShopCreateEvent extends ApplicationEvent {

    private Shop shop;

    public ShopCreateEvent(Object source, Shop shop) {
        super(source);
        this.shop = shop;
    }

}
