package com.shoppingcenter.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {

    public enum Status {
        PENDING, ACTIVE, EXPIRED
    }

    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;
    
    private double rating;

    private boolean featured;
    
    private boolean disabled;
    
    private boolean activated;
    
    private boolean expired;

    private String logo;

    private String cover;

    private ShopContact contact;
    
    private ShopStatistic statistic;

    private long createdAt;

    public Shop() {
    }

}
