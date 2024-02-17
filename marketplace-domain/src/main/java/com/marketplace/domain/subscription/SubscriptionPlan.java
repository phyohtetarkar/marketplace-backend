package com.marketplace.domain.subscription;

import java.math.BigDecimal;

import com.marketplace.domain.Audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlan {

    private long id;

    private String title;

    private int duration; // by days

    private boolean promoUsable;

    private BigDecimal price;

    private Audit audit = new Audit();
    
    public SubscriptionPlan() {
	}

}
