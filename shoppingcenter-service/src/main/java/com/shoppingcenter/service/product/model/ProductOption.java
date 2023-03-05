package com.shoppingcenter.service.product.model;

import com.shoppingcenter.data.product.ProductOptionEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOption {

    private long id;

    private Long productId;

    private String name;

    private int position;

    public static ProductOption create(ProductOptionEntity entity) {
        ProductOption op = new ProductOption();
        op.setName(entity.getName());
        op.setPosition(entity.getPosition());
        return op;
    }
}
