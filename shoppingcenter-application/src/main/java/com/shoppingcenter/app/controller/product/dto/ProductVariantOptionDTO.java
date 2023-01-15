package com.shoppingcenter.app.controller.product.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantOptionDTO {

    private String option;

    private String value;

    public static Type listType() {
        return new TypeToken<List<ProductVariantOptionDTO>>() {
        }.getType();
    }
}
