package com.shoppingcenter.core.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.data.product.ProductImageEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {

    private long id;

    private String name;

    private boolean thumbnail;

    @JsonProperty(access = Access.WRITE_ONLY)
    private UploadFile file;

    @JsonProperty(access = Access.WRITE_ONLY)
    private boolean deleted;

    public static ProductImage create(ProductImageEntity entity, String baseUrl) {
        return null;
    }
}
