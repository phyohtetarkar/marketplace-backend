package com.shoppingcenter.app.controller.category.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.PageDataDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private int id;
    
    private int lft;
    
    private int rgt;

    private String name;

    private String slug;

    private String image;

    private Boolean featured;

    private CategoryDTO category;

    private List<CategoryDTO> children;

    private Integer categoryId;

    private long createdAt;

    public static Type listType() {
        return new TypeToken<List<CategoryDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageDataDTO<CategoryDTO>>() {
        }.getType();
    }

}
