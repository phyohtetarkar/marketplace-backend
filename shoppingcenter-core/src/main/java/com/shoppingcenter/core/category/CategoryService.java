package com.shoppingcenter.core.category;

import java.util.List;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.category.model.Category;

public interface CategoryService {

	void save(Category category);

	void delete(int id);

	Category findById(int id);

	Category findBySlug(String slug);

	List<Category> findHierarchical();

	List<Category> findMainCategories();

	List<Category> findAll();

	PageData<Category> findAll(Integer page);
}
