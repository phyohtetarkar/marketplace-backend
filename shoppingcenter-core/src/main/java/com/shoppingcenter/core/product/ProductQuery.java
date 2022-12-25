package com.shoppingcenter.core.product;

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

    private Integer page;

    public Integer getPage() {
        return page != null && page > 0 ? page : 1;
    }

}
