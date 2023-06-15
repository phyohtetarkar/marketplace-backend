package com.shoppingcenter.domain.shop;

import java.util.List;

import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.misc.City;

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
    
    private String phone;
    
    private boolean cashOnDelivery;
    
    private boolean bankTransfer;
    
    private List<ShopAcceptedPayment> acceptedPayments;
    
    private List<City> deliveryCities;
    
    //private long subscriptionPlanId;
    
    private long cityId;
    
    private UploadFile logo;

    private UploadFile cover;
    
    public ShopCreateInput() {
		// TODO Auto-generated constructor stub
	}
	
}
