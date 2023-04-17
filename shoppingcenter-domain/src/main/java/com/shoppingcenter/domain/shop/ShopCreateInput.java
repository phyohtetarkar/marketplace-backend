package com.shoppingcenter.domain.shop;

import com.shoppingcenter.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCreateInput {
	
	private long userId;

	private String name;

    private String slug;

    private String headline;

    private String about;
    
    private String address;
    
    private long subscriptionPlanId;
    
    private UploadFile logo;

    private UploadFile cover;
	
}
