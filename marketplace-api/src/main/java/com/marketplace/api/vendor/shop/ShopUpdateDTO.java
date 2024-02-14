package com.marketplace.api.vendor.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopUpdateDTO {
	@JsonIgnore
	private long shopId;
	
    private String name;

    private String slug;

    private String headline;

    private String about;
}
