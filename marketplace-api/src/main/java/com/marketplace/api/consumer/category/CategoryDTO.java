package com.marketplace.api.consumer.category;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.CategoryImageSerializer;

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

}
