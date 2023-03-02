package com.shoppingcenter.search.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDocument {

    private long entityId;

    private String name;

    private boolean thumbnail;

    private long size;
}
