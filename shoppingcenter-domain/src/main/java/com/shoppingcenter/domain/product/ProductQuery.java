package com.shoppingcenter.domain.product;

import com.shoppingcenter.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductQuery {
    private String q;

    private Boolean discount;

    private String[] brands;

    private Double maxPrice;

    private String categorySlug;

    private Boolean hidden;

    private Boolean disabled;

    private Long shopId;

    private Long discountId;

    private Integer categoryId;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }
}
