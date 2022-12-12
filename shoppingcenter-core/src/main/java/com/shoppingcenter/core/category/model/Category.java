package com.shoppingcenter.core.category.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.data.category.CategoryEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

	private int id;

	private String name;

	private String slug;

	@JsonProperty(access = Access.READ_ONLY)
	private String image;

	@JsonProperty(access = Access.READ_ONLY)
	private int level;

	@JsonProperty(access = Access.READ_ONLY)
	private List<Category> children;

	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer categoryId;

	@JsonProperty(access = Access.WRITE_ONLY)
	private UploadFile file;

	public Category() {
	}

	public static Category create(CategoryEntity entity, String baseUrl) {
		Category c = new Category();
		c.setId(entity.getId());
		c.setName(entity.getName());
		c.setSlug(entity.getSlug());
		c.setImage(entity.getImage() + "categories/" + entity.getImage());
		c.setLevel(entity.getLevel());

		if (entity.getCategories() != null) {
			c.setChildren(
					entity.getCategories().stream().map(e -> Category.create(e, baseUrl)).collect(Collectors.toList()));
		}

		return c;
	}
}
