package com.marketplace.domain.shop;

import com.marketplace.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopQuery {

    private String q;
    
    private Long cityId;

    private Boolean expired;
    
    private Boolean featured;
    
    private Shop.Status status;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }

}
