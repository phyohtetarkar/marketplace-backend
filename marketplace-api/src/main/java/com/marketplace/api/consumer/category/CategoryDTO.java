package com.marketplace.api.consumer.category;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.CategoryImageSerializer;
import com.marketplace.api.PageDataDTO;

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

    @JsonSerialize(using = CategoryImageSerializer.class)
    private String image;

    private Boolean featured;

    private CategoryDTO category;

    private List<CategoryDTO> children;
    
    private List<CategoryNameDTO> names;

    public static Type listType() {
        return new TypeToken<List<CategoryDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageDataDTO<CategoryDTO>>() {
        }.getType();
    }

}
