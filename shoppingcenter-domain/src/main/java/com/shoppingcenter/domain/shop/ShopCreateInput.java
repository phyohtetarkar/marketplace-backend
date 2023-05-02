package com.shoppingcenter.domain.shop;

import java.util.List;

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
    
    private String deliveryNote;
    
    private boolean cashOnDelivery;
    
    private boolean bankTransfer;
    
    private List<ShopAcceptedPayment> acceptedPayments;
    
    private long subscriptionPlanId;
    
    private UploadFile logo;

    private UploadFile cover;
	
}
