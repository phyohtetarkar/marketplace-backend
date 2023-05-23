package com.shoppingcenter.domain.product;

import java.math.BigDecimal;

import com.shoppingcenter.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductQuery {
    private String q;

    private Boolean discount;

    private String[] brands;

    private BigDecimal maxPrice;
    
    private Integer stockLessThan;

    private Product.Status status;

    private Boolean disabled;
    
    private Long shopId;

    private Long discountId;

    private Integer categoryId;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }
}
