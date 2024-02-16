package com.marketplace.domain.category.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDao;

@Component
public class GetCategoryByIdUseCase {

	@Autowired
	private CategoryDao categoryDao;

	@Transactional(readOnly = true)
	public Category apply(int id) {
		var category = categoryDao.findById(id);
		if (category == null) {
			throw ApplicationException.notFound("Category not found");
		}
		return category;
	}

}
