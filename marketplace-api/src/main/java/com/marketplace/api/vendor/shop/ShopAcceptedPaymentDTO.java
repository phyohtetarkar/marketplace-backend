package com.marketplace.api.vendor.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopAcceptedPaymentDTO {

	private long id;

    private String accountType;
    
    private String accountName;

    private String accountNumber;
    
}
