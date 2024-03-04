package com.marketplace.api.consumer.subscription;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlanDTO {

    private long id;

    private String title;

    private int duration;

    private boolean promoUsable;

    private BigDecimal price;
}
