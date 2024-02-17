package com.marketplace.domain.category.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDao;

@Component
public class GetRootCategoriesUseCase {

	@Autowired
    private CategoryDao dao;

	@Transactional(readOnly = true)
    public List<Category> apply() {
        return dao.findRootCategories();
    }

}
