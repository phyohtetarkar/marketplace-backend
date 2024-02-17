package com.marketplace.api.vendor.shop;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopContactUpdateDTO {
	
	@JsonIgnore
	private long shopId;

	private List<String> phones;

    private String address;

    private Double latitude;

    private Double longitude;
    
    private long cityId;
    
}
