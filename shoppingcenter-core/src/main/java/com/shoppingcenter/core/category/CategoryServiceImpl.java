package com.shoppingcenter.core.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.category.model.Category;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.category.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo repo;

	@Value("${app.image.base-url}")
	private String baseUrl;

	@Transactional
	@Override
	public void save(Category category) {
		try {
			CategoryEntity entity = repo.findById(category.getId()).orElseGet(CategoryEntity::new);
			entity.setName(category.getName());
			entity.setSlug(category.getSlug());

			if (category.getCategoryId() != null) {
				CategoryEntity parent = repo.findById(category.getCategoryId())
						.orElseThrow(() -> new ApplicationException("Parent category not found."));
				entity.setCategory(parent);
				entity.setLevel(parent.getLevel() + 1);
			}

			repo.save(entity);

			// TODO: upload image
		} catch (Exception e) {
			throw new ApplicationException("Failed to save category.");
		}
	}

	@Transactional
	@Override
	public void delete(int id) {
		CategoryEntity entity = repo.findById(id).orElseThrow(() -> new ApplicationException("Category not found."));

		repo.deleteById(id);

		// TODO: delete image
	}

	@Override
	public Category findById(int id) {
		return repo.findById(id).map(e -> Category.create(e, baseUrl)).orElse(null);
	}

	@Override
	public Category findBySlug(String slug) {
		return repo.findBySlug(slug).map(e -> Category.create(e, baseUrl)).orElse(null);
	}

	@Override
	public List<Category> findHierarchical() {
		return null;
	}

	@Override
	public List<Category> findMainCategories() {
		return repo.findByCategoryNull().stream().map(e -> Category.create(e, baseUrl)).collect(Collectors.toList());
	}

}
