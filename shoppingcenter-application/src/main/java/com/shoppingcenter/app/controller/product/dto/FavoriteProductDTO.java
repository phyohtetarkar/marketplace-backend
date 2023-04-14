package com.shoppingcenter.app.controller.product.dto;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;

import com.shoppingcenter.domain.PageData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteProductDTO {

    private long productId;

    private ProductDTO product;

    public static Type pageType() {
        return new TypeToken<PageData<FavoriteProductDTO>>() {
        }.getType();
    }

}
