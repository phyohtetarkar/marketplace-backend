package com.shoppingcenter.service.shop;

import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.shop.model.Shop;

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
