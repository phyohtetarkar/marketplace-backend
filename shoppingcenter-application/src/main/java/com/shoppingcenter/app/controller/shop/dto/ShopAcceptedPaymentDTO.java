package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopAcceptedPaymentDTO {

	private long id;

    private long shopId;

    private String accountType;

    private String accountNumber;
    
    public static Type listType() {
        return new TypeToken<List<ShopAcceptedPaymentDTO>>() {
        }.getType();
    }
    
}