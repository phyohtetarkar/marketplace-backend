package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;

public class GetCategoryBySlugUseCaseImpl implements GetCategoryBySlugUseCase {

    private CategoryDao dao;

    public GetCategoryBySlugUseCaseImpl(CategoryDao dao) {
        this.dao = dao;
    }

    @Override
    public Category apply(String slug) {
        Category category = dao.findBySlug(slug);

        if (category == null) {
            throw new ApplicationException("Category not found");
        }

        return category;
    }

}
