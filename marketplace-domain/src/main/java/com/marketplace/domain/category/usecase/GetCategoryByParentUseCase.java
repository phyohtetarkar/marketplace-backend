package com.marketplace.domain.category.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDao;

@Component
public class GetCategoryByParentUseCase {

	@Autowired
	private CategoryDao dao;
	
	@Transactional(readOnly = true)
	public List<Category> apply(int categoryId) {
		if (categoryId > 0) {
			return dao.findByCategory(categoryId);
		}
		
		return dao.findRootCategories();
	}
}
