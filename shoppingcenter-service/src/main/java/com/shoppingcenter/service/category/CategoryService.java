package com.shoppingcenter.service.category;

import java.util.List;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.category.model.Category;

public interface CategoryService {

	void save(Category category);

	void delete(int id);

	Category findById(int id);

	Category findBySlug(String slug);

	boolean existsBySlug(String slug, Integer excludeId);

	List<Category> findHierarchical();

	List<Category> findMainCategories();

	List<Category> findFlat();

	PageData<Category> findAll(Integer page);
}
