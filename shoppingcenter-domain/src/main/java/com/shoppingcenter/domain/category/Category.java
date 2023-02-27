package com.shoppingcenter.domain.category;

import java.util.List;

import com.shoppingcenter.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

	private int id;

	private String name;

	private String slug;

	private String image;

	private String imageUrl;

	private boolean featured;

	private Category category;

	private List<Category> children;

	private Integer categoryId;

	private Long createdAt;

	private UploadFile file;
}
