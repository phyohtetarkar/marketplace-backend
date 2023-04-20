package com.shoppingcenter.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopAcceptedPayment {

    private long id;

    private long shopId;

    private String accountType;

    private String accountNumber;

}