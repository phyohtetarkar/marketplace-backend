package com.shoppingcenter.domain.shop;

import com.shoppingcenter.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {

    public enum Status {
        PENDING, ACTIVE, SUBSCRIPTION_EXPIRED, DISABLED
    }

    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;

    private double rating;

    private ShopContact contact;

    private boolean featured;

    private String logo;

    private String cover;

    private Status status;

    private String address;

    private long subscriptionPlanId;

    private UploadFile logoImage;

    private UploadFile coverImage;

    private Long createdAt;

    public Shop() {
        this.status = Status.PENDING;
    }

}
