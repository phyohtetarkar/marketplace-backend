package com.marketplace.domain.discount;

import java.math.BigDecimal;

import com.marketplace.domain.Audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Discount {

    public enum Type {
        PERCENTAGE, FIXED_AMOUNT
    }

    private long id;
    
    private long shopId;

    private String title;

    private BigDecimal value;

    private Type type;

    private Audit audit = new Audit();
}
