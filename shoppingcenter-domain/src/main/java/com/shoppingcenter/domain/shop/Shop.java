package com.shoppingcenter.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {

    public enum Status {
        PENDING, ACTIVE, DISABLED, EXPIRED
    }

    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;

    private double rating;

    private boolean featured;

    private String logo;

    private String cover;

    private Status status;
    
    private ShopContact contact;

    private long createdAt;

    public Shop() {
        this.status = Status.PENDING;
    }

}
