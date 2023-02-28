package com.shoppingcenter.domain.product;

import com.shoppingcenter.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {

    private long id;

    private Long productId;

    private String name;

    private String url;

    private boolean thumbnail;

    private long size;

    private UploadFile file;

    private boolean deleted;
}
