package com.shoppingcenter.core.product.model;

import org.springframework.util.StringUtils;

import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.data.product.ProductImageEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {

    private long productId;

    private String createdAt;

    private String name;

    private boolean thumbnail;

    private UploadFile file;

    private boolean deleted;

    public static ProductImage create(ProductImageEntity entity, String baseUrl) {
        ProductImage image = new ProductImage();
        image.setProductId(entity.getProductId());
        image.setCreatedAt(entity.getCreatedAt());
        image.setThumbnail(entity.isThumbnail());
        if (StringUtils.hasText(entity.getName())) {
            image.setName(baseUrl + entity.getName());
        }
        return image;
    }
}
