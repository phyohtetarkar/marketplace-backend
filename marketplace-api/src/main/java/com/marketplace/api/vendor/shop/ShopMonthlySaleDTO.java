package com.marketplace.api.vendor.shop;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMonthlySaleDTO {
	
	private long shopId;

	private int year;

	private int month;

	private double totalSale;
	
	public static Type listType() {
        return new TypeToken<List<ShopMonthlySaleDTO>>() {
        }.getType();
    }

}
