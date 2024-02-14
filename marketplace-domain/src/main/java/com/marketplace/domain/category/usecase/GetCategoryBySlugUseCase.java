package com.marketplace.domain.category.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDao;

@Component
public class GetCategoryBySlugUseCase {

	@Autowired
	private CategoryDao dao;

	@Transactional(readOnly = true)
	public Category apply(String slug) {
		var category = dao.findBySlug(slug);
		if (category == null) {
			throw ApplicationException.notFound("Category not found");
		}
		
//		var c = category;
//		
//		do {
//			var names = c.getNames();
//			if (names != null) {
//				var localizedName = names.stream().filter(n -> n.getLang().equals(lang))
//						.findFirst()
//						.map(CategoryName::getName)
//						.orElse(null);
//				c.setName(localizedName);
//			}
//			c = c.getCategory();
//		} while (c != null);
		
		
		return category;
	}

}
