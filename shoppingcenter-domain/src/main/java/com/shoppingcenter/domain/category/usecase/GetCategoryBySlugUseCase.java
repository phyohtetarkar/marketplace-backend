package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;

public class GetCategoryBySlugUseCase {

    private CategoryDao dao;

    public GetCategoryBySlugUseCase(CategoryDao dao) {
        this.dao = dao;
    }

    public Category apply(String slug) {
        var category = dao.findBySlug(slug);

//        if (category == null) {
//            throw new ApplicationException("Category not found");
//        }

        return category;
    }

}
