package com.marketplace.domain.product;

import java.math.BigDecimal;

import com.marketplace.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductQuery {
    private String q;

    private Boolean discount;
    
    private Boolean featured;
    
    private Boolean newArrival;

    private String[] brands;

    private BigDecimal maxPrice;

    private Product.Status status;
    
    private Long shopId;

    private Long discountId;

    private Integer categoryId;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }
}
