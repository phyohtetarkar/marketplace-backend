package com.marketplace.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopUpdateInput {

    private long shopId;
    
    private String name;

    private String slug;

    private String headline;

    private String about;
    
}
