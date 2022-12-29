package com.shoppingcenter.core.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.category.model.Category;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.category.CategoryRepo;
import com.shoppingcenter.data.product.ProductRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ProductRepo productRepo;

	@Value("${app.image.base-url}")
	private String baseUrl;

	@Transactional
	@Override
	public void save(Category category) {
		try {
			CategoryEntity entity = categoryRepo.findById(category.getId()).orElseGet(CategoryEntity::new);
			entity.setName(category.getName());
			entity.setSlug(category.getSlug());

			if (category.getCategoryId() != null) {
				if (!categoryRepo.existsById(category.getCategoryId())) {
					throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
				}
				CategoryEntity parent = categoryRepo.getReferenceById(category.getCategoryId());
				entity.setCategory(parent);
				entity.setLevel(parent.getLevel() + 1);
			}

			categoryRepo.save(entity);

			// TODO: upload image
		} catch (Exception e) {
			throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, "Failed to save category");
		}
	}

	@Transactional
	@Override
	public void delete(int id) {
		if (!categoryRepo.existsById(id)) {
			throw new ApplicationException(ErrorCodes.NOT_FOUND, "Category not found");
		}

		if (categoryRepo.existsByCategory_Id(id)) {
			throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, "Referenced by categories");
		}

		if (productRepo.existsByCategory_Id(id)) {
			throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, "Referenced by products");
		}

		CategoryEntity entity = categoryRepo.getReferenceById(id);
		String image = entity.getImage();

		categoryRepo.deleteById(id);

		// TODO: delete image
	}

	@Override
	public Category findById(int id) {
		return categoryRepo.findById(id).map(e -> Category.create(e, baseUrl)).orElse(null);
	}

	@Override
	public Category findBySlug(String slug) {
		return categoryRepo.findBySlug(slug).map(e -> Category.create(e, baseUrl)).orElse(null);
	}

	@Override
	public List<Category> findSubCategories(int categoryId) {
		return null;
	}

	@Override
	public List<Category> findHierarchical() {
		List<CategoryEntity> categories = categoryRepo.findByCategoryNull();

		for (CategoryEntity entity : categories) {
			List<CategoryEntity> childCategories = entity.getCategories();

			for (CategoryEntity childEntity : childCategories) {
				childEntity.getCategories();
			}
		}

		return categories.stream().map(e -> Category.createComplete(e, baseUrl)).collect(Collectors.toList());
	}

	@Override
	public List<Category> findMainCategories() {
		return categoryRepo.findByCategoryNull().stream().map(e -> Category.createCompat(e, baseUrl))
				.collect(Collectors.toList());
	}

}
