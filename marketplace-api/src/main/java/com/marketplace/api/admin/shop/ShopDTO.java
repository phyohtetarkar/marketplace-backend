package com.marketplace.api.admin.shop;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.ShopImageSerializer;
import com.marketplace.api.AuditDTO;
import com.marketplace.api.consumer.general.CityDTO;
import com.marketplace.api.consumer.shop.ShopContactDTO;
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

    private double rating;

    private boolean featured;
    
    private Shop.Status status;

    @JsonSerialize(using = ShopImageSerializer.class)
    private String logo;

    @JsonSerialize(using = ShopImageSerializer.class)
    private String cover;
    
    private long expiredAt;
    
    private ShopContactDTO contact;
    
    private CityDTO city;
    
    private AuditDTO audit;
}
