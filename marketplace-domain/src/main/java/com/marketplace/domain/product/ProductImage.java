package com.marketplace.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {

    private long id;

    private String name;

    private boolean thumbnail;

    private long size;

}
