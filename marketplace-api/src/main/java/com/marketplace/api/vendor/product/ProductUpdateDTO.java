package com.marketplace.api.vendor.product;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateDTO {

	private long id;

    private String sku;

    private String name;

    private String slug;

    private String brand;

    private BigDecimal price;

    private boolean available;

    private boolean newArrival;

    private boolean withVariant;

    private Long discountId;

    private int categoryId;

    @JsonIgnore
    private long shopId;
    
}
