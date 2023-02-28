package com.shoppingcenter.domain.product;

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
        DRAFT, PUBLISHED, ARCHIVED, DISABLED
    }

    private long id;

    private String sku;

    private String name;

    private String slug;

    private String brand;

    private String priceRange;

    private Double price;

    private int stockLeft;

    private String thumbnail;

    private boolean featured;

    private boolean newArrival;

    private boolean withVariant;

    private String description;

    private Status status;

    private List<ProductOption> options;

    private List<ProductVariant> variants;

    private List<ProductImage> images;

    private Discount discount;

    private Category category;

    private Shop shop;

    private Long discountId;

    private int categoryId;

    private long shopId;

    private Long createdAt;

    public Product() {
        this.status = Status.DRAFT;
    }
}
