package com.shoppingcenter.app.controller.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {

    private long id;

    private long productId;

    private String name;

    private boolean thumbnail;
}
