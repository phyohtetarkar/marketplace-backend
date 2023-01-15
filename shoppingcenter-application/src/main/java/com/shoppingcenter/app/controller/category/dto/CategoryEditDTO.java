package com.shoppingcenter.app.controller.category.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryEditDTO {
    private int id;

    private String name;

    private String slug;

    private Integer categoryId;

    private MultipartFile file;
}