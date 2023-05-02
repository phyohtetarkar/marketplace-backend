package com.shoppingcenter.domain.shop;

import com.shoppingcenter.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopQuery {

    private String q;

    private Boolean disabled;
    
    private Boolean expired;
    
    private Boolean activated;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }

}
