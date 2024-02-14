package com.marketplace.domain.shop;

import java.util.List;

import com.marketplace.domain.UploadFile;

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
    
    private List<ShopAcceptedPaymentInput> acceptedPayments;
    
    private long cityId;
    
    private UploadFile logo;

    private UploadFile cover;
    
    public ShopCreateInput() {
	}
	
}
