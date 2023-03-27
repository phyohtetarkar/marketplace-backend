package com.shoppingcenter.app.controller.product.dto;

import java.util.List;

import com.shoppingcenter.domain.product.Product;

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

    private Product.Status status;

    private String description;

    private List<ProductOptionDTO> options;

    private List<ProductVariantDTO> variants;

    private List<ProductImageEditDTO> images;

    private Long discountId;

    private int categoryId;

    private long shopId;
}
