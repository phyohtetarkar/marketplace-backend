package com.shoppingcenter.domain.category.usecase;

import java.util.List;

import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;

public class GetRootCategoriesUseCase {

    private CategoryDao dao;

    public GetRootCategoriesUseCase(CategoryDao dao) {
        this.dao = dao;
    }

    public List<Category> apply() {
        return dao.findRootCategories();
    }

}
