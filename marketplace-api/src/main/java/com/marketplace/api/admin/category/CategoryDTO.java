package com.marketplace.api.admin.category;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.CategoryImageSerializer;
import com.marketplace.api.AuditDTO;
import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.category.CategoryNameDTO;

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
    
    private List<CategoryNameDTO> names;
    
    private AuditDTO audit;

    public static Type listType() {
        return new TypeToken<List<CategoryDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageDataDTO<CategoryDTO>>() {
        }.getType();
    }

}
