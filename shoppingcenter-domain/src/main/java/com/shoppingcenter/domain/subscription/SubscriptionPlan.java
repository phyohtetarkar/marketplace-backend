package com.shoppingcenter.domain.subscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlan {

    private long id;

    private String title;

    private int duration; // by days

    private boolean promoUsable;

    private double price;

    private Long createdAt;

}
