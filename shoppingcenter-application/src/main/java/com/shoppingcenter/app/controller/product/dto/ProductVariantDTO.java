package com.shoppingcenter.app.controller.product.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantDTO {

    private String id;

    private long productId;

    private String title;

    private String sku;

    private double price;

    private boolean outOfStock;

    private List<ProductVariantOptionDTO> options;
}
