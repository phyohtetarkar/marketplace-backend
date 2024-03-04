package com.marketplace.api.admin.subscription;

import java.math.BigDecimal;

import com.marketplace.api.AuditDTO;

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
    
    private AuditDTO audit;
}
