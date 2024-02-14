package com.marketplace.api.vendor.discount;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marketplace.domain.discount.Discount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountEditDTO {
	
    private long id;
    
    @JsonIgnore
    private long shopId;

    private String title;

    private BigDecimal value;

    private Discount.Type type;
    
}
