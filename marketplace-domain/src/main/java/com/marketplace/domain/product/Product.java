package com.marketplace.domain.product;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.domain.Audit;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.discount.Discount;
import com.marketplace.domain.shop.Shop;

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

    private String thumbnail;

    private boolean featured;

    private boolean newArrival;

    private boolean withVariant;

    private String description;
    
    private String videoEmbed;
    
    private boolean deleted;
    
    private boolean available;
    
    private Product.Status status;
    
    private List<ProductImage> images;

    private List<ProductAttribute> attributes;

    private List<ProductVariant> variants;

    private Discount discount;

    private Category category;

    private Shop shop;

    private Audit audit = new Audit();

    public Product() {
    	this.status = Status.DRAFT;
    }
}
