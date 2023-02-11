package com.shoppingcenter.app.controller.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDTO {

    private long id;

    private Long productId;

    private String name;

    private int position;
}
