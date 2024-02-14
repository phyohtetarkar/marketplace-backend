package com.marketplace.domain.subscription;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlanInput {

	private long id;

    private String title;

    private int duration; // by days

    private boolean promoUsable;

    private BigDecimal price;
    
    
}
