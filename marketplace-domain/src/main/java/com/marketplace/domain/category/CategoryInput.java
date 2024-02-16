package com.marketplace.domain.category;

import java.util.List;

import com.marketplace.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryInput {

	private int id;
	
	private List<CategoryName> names;

	private String slug;

	private Integer categoryId;

	private boolean featured;

	private UploadFile file;
	
}
