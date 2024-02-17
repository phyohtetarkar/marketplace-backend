package com.marketplace.domain.product;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateInput {

	private long id;

    private String sku;

    private String name;

    private String slug;

    private String brand;

    private BigDecimal price;
    
    private boolean available;

    private boolean newArrival;

    private Long discountId;

    private int categoryId;
    
    private long shopId;
    
    public ProductUpdateInput() {
	}
    
}
