package com.marketplace.api.admin.category;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.marketplace.api.consumer.category.CategoryNameDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryEditDTO {
	
    private int id;

    private String slug;
    
    private String lang;

    private Integer categoryId;

    private boolean featured;
    
    private List<CategoryNameDTO> names;

    private MultipartFile file;
}
