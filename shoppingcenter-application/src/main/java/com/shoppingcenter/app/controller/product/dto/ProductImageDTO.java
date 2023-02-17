package com.shoppingcenter.app.controller.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {

    private long id;

    private Long productId;

    private String name;

    private String url;

    private boolean thumbnail;
}
