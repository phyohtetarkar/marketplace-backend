package com.shoppingcenter.service.shop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscription {

    public enum Status {
        PENDING, FAILED, ACTIVE, EXPIRED
    }

    public ShopSubscription() {

    }
}
