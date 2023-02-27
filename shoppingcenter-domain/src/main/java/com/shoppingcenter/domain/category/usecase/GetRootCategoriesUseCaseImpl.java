package com.shoppingcenter.domain.category.usecase;

import java.util.List;

import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;

public class GetRootCategoriesUseCaseImpl implements GetRootCategoriesUseCase {

    private CategoryDao dao;

    public GetRootCategoriesUseCaseImpl(CategoryDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Category> apply() {
        return dao.findRootCategories();
    }

}
