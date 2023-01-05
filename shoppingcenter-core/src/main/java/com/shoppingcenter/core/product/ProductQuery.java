package com.shoppingcenter.core.product;

import com.shoppingcenter.core.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductQuery {

    private String q;

    private Boolean discount;

    private String brand;

    private Double maxPrice;

    private String categorySlug;

    private Long shopId;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }

}
