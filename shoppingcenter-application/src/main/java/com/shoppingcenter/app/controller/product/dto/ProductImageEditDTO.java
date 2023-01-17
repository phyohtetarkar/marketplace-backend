package com.shoppingcenter.app.controller.product.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageEditDTO {

    private String id;

    private long productId;

    private String name;

    private boolean thumbnail;

    private MultipartFile file;

    private boolean deleted;
}
