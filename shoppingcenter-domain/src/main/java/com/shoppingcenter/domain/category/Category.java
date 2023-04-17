package com.shoppingcenter.domain.category;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

	private int id;

	private int lft;

	private int rgt;

	private String name;

	private String slug;
	
	private String image;

	private boolean featured;
	
	private Integer categoryId;

	private Category category;

	private List<Category> children;

	private long createdAt;

	
}
