package com.shoppingcenter.service.category.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.service.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

	private int id;

	private String name;

	private String slug;

	private String image;

	private boolean featured;

	private Category category;

	private List<Category> children;

	private Integer categoryId;

	private long createdAt;

	private UploadFile file;

	public Category() {
	}

	public static Category create(CategoryEntity entity, String baseUrl) {
		Category c = createCompat(entity, baseUrl);
		if (entity.getCategory() != null) {
			c.setCategory(Category.create(entity.getCategory(), baseUrl));
		}
		return c;
	}

	public static Category createComplete(CategoryEntity entity, String baseUrl) {
		Category c = createCompat(entity, baseUrl);

		if (entity.getCategories() != null && !entity.getCategories().isEmpty()) {
			c.setChildren(
					entity.getCategories().stream().map(e -> Category.createComplete(e, baseUrl))
							.collect(Collectors.toList()));
		}

		return c;
	}

	public static Category createCompat(CategoryEntity entity, String baseUrl) {
		Category c = new Category();
		c.setId(entity.getId());
		c.setName(entity.getName());
		c.setSlug(entity.getSlug());
		c.setFeatured(entity.isFeatured());
		c.setCategoryId(Optional.ofNullable(entity.getCategory()).map(CategoryEntity::getId).orElse(null));
		c.setCreatedAt(entity.getCreatedAt());

		if (StringUtils.hasText(entity.getImage())) {
			c.setImage(baseUrl + "category/" + entity.getImage());
		}
		return c;
	}
}
