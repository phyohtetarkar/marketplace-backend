package com.marketplace.api.admin.subscription;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlanEditDTO {

    private long id;

    private String title;

    private int duration;

    private boolean promoUsable;

    private BigDecimal price;
}
