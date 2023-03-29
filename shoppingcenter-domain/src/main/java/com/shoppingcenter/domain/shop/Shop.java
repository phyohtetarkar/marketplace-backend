package com.shoppingcenter.domain.shop;

import com.shoppingcenter.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {

    public enum Status {
        PENDING, ACTIVE
    }

    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;

    private double rating;

    private ShopContact contact;

    private Boolean featured;

    private boolean expired;

    private boolean disabled;

    private String logo;

    private String logoUrl;

    private String cover;

    private String coverUrl;

    private Status status;

    private String address;

    private Integer pendingOrderCount;

    private long subscriptionPlanId;

    private UploadFile logoImage;

    private UploadFile coverImage;

    private Long createdAt;

    public Shop() {
        this.status = Status.PENDING;
    }

}
