package com.marketplace.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopAcceptedPayment {

    private long id;
    
    private String accountType;
    
    private String accountName;

    private String accountNumber;

}
