package com.marketplace.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopAcceptedPaymentInput {

	private long id;
    
    private long shopId;

    private String accountType;
    
    private String accountName;

    private String accountNumber;
    
}
