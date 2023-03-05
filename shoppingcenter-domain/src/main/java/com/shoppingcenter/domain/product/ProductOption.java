package com.shoppingcenter.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOption {

    private Long productId;

    private String name;

    private int position;
}
