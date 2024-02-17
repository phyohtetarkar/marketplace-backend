package com.marketplace.api.vendor.shop;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopContactDTO {

    private List<String> phones;

    private String address;

    private Double latitude;

    private Double longitude;
    
}
