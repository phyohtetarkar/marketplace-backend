package com.marketplace.api.consumer.shop;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.TypeToken;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.ShopImageSerializer;
import com.marketplace.api.PageDataDTO;
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
    
    public static Type listType() {
        return new TypeToken<List<ShopDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageDataDTO<ShopDTO>>() {
        }.getType();
    }
}
