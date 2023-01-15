package com.shoppingcenter.app.controller.product.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {

    private long productId;

    private String createdAt;

    private String name;

    private boolean thumbnail;

    public static Type listType() {
        return new TypeToken<List<ProductImageDTO>>() {
        }.getType();
    }
}
