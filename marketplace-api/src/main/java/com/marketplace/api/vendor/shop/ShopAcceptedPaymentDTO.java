package com.marketplace.api.vendor.shop;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopAcceptedPaymentDTO {

	private long id;

    private String accountType;
    
    private String accountName;

    private String accountNumber;
    
    public static Type listType() {
        return new TypeToken<List<ShopAcceptedPaymentDTO>>() {
        }.getType();
    }
    
}
