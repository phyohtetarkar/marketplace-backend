package com.shoppingcenter.app.controller.product.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDTO {

    private long productId;

    private String name;

    private int position;

    public static Type listType() {
        return new TypeToken<List<ProductOptionDTO>>() {
        }.getType();
    }
}
