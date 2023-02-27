package com.shoppingcenter.domain.shop;

import com.shoppingcenter.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopQuery {

    private String q;

    private Shop.Status status;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }

}
