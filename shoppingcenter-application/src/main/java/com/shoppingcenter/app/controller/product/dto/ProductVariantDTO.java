package com.shoppingcenter.app.controller.product.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantDTO {

    private long productId;

    private String optionPath;

    private String title;

    private String sku;

    private double price;

    private boolean outOfStock;

    private List<ProductVariantOptionDTO> options;

    public static Type listType() {
        return new TypeToken<List<ProductVariantDTO>>() {
        }.getType();
    }
}
