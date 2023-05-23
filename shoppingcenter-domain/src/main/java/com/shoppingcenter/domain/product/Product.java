package com.shoppingcenter.domain.product;

import java.math.BigDecimal;
import java.util.List;

import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.discount.Discount;
import com.shoppingcenter.domain.shop.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	
	public enum Status {
		DRAFT, PUBLISHED
	}

    private long id;

    private String sku;

    private String name;

    private String slug;

    private String brand;

    private BigDecimal price;

    private Integer stockLeft;

    private String thumbnail;

    private boolean featured;

    private boolean newArrival;

    private boolean withVariant;

    private String description;
    
    private String videoUrl;

    private boolean disabled;
    
    private Product.Status status;
    
    private List<ProductImage> images;

    private List<ProductAttribute> attributes;

    private List<ProductVariant> variants;

    private Discount discount;

    private Category category;

    private Shop shop;

    private long createdAt;

    public Product() {
    	this.status = Status.DRAFT;
    }
}
