package com.shoppingcenter.domain.product;

import com.shoppingcenter.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {

    private long id;

    private String name;

    private boolean thumbnail;

    private long size;

    private long productId;
    
    private long shopId;

    private UploadFile file;

    private boolean deleted;

}
