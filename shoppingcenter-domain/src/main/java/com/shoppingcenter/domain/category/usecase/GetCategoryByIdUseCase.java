package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;

public class GetCategoryByIdUseCase {

	private CategoryDao categoryDao;

	public GetCategoryByIdUseCase(CategoryDao categoryDao) {
		super();
		this.categoryDao = categoryDao;
	}

	public Category apply(int id) {
		return categoryDao.findById(id);
	}

}
