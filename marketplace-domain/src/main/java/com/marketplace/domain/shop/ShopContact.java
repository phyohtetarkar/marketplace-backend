package com.marketplace.domain.shop;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopContact {

    private List<String> phones;

    private String address;

    private Double latitude;

    private Double longitude;
    
}
