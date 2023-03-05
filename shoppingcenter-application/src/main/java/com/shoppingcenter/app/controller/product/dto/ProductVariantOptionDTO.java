package com.shoppingcenter.app.controller.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantOptionDTO {

    private Long variantId;

    private String option;

    private String value;
}
