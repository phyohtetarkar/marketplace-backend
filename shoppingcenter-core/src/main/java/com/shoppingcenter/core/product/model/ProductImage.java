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

    private long productId;

    @JsonProperty(access = Access.READ_ONLY)
    private String createdAt;

    private String name;

    private boolean thumbnail;

    @JsonProperty(access = Access.WRITE_ONLY)
    private UploadFile file;

    @JsonProperty(access = Access.WRITE_ONLY)
    private boolean deleted;

    public static ProductImage create(ProductImageEntity entity, String baseUrl) {
        ProductImage image = new ProductImage();
        image.setProductId(entity.getProductId());
        image.setCreatedAt(entity.getCreatedAt());
        image.setName(baseUrl + entity.getName());
        image.setThumbnail(entity.isThumbnail());
        return image;
    }
}
