package com.shoppingcenter.app.controller.product.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEditDTO {

    private long id;

    private String sku;

    private String name;

    private String slug;

    private String brand;

    private Double cost;

    private Double price;

    private int stockLeft;

    private boolean featured;

    private boolean newArrival;

    private boolean withVariant;

    private boolean hidden;

    private String description;
    
    private String videoUrl;

    private List<ProductOptionDTO> options;

    private List<ProductVariantDTO> variants;

    private List<ProductImageEditDTO> images;

    private Long discountId;

    private int categoryId;

    private long shopId;
}
