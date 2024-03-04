package com.marketplace.api.consumer.shop;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.ShopImageSerializer;
import com.marketplace.api.consumer.general.CityDTO;
import com.marketplace.domain.shop.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDTO {
    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;

    private BigDecimal rating;

    private boolean featured;
    
    private Shop.Status status;

    @JsonSerialize(using = ShopImageSerializer.class)
    private String logo;

    @JsonSerialize(using = ShopImageSerializer.class)
    private String cover;
    
    private ShopContactDTO contact;
    
    private CityDTO city;
    
    private long expiredAt;
}
