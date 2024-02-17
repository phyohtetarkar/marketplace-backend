package com.marketplace.api.vendor.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopStatisticDTO {
	
	private long pendingOrder;
	
    private double totalSale;

    private long totalOrder;

    private long totalProduct;

}
