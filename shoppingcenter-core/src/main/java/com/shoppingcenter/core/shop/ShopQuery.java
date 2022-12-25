package com.shoppingcenter.core.shop;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopQuery {

    private String q;

    private Integer page;

    public Integer getPage() {
        return page != null && page > 0 ? page : 1;
    }
}
