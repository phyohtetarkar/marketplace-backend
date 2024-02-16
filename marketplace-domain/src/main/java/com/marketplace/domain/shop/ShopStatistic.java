package com.marketplace.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopStatistic {
	
	private long pendingOrder;
	
    private double totalSale;

    private long totalOrder;

    private long totalProduct;
    
}
