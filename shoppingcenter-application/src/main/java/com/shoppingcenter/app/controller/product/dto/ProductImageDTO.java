package com.shoppingcenter.app.controller.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {

    private long productId;

    private String createdAt;

    private String name;

    private boolean thumbnail;
}
