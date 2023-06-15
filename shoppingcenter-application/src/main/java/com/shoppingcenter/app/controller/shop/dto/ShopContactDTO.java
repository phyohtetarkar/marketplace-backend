package com.shoppingcenter.app.controller.shop.dto;

import java.util.List;

import com.shoppingcenter.app.controller.misc.dto.CityDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopContactDTO {
	
	private long shopId;

    private List<String> phones;

    private String address;

    private Double latitude;

    private Double longitude;
    
    private CityDTO city;
    
}
