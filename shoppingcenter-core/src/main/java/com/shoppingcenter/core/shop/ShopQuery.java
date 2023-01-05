package com.shoppingcenter.core.shop;

import com.shoppingcenter.core.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopQuery {

    private String q;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }
}
