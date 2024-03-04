package com.marketplace.api.admin.category;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.CategoryImageSerializer;
import com.marketplace.api.AuditDTO;
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

}
