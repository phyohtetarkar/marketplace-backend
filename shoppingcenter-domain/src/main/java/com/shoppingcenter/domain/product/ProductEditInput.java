package com.shoppingcenter.domain.product;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEditInput {

	private long id;

    private String sku;

    private String name;

    private String slug;

    private String brand;

    private BigDecimal price;

    private int stockLeft;

    private String thumbnail;

    private boolean featured;

    private boolean newArrival;

    private boolean withVariant;

    private String description;
    
    private String videoUrl;

    private boolean hidden;

    private List<ProductImage> images;

    private List<ProductAttribute> attributes;

    private List<ProductVariant> variants;

    private Long discountId;

    private int categoryId;

    private long shopId;
    
    public ProductEditInput() {
	}
    
}
