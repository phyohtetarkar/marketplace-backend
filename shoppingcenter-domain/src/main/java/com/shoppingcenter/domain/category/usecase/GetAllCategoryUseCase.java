package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;

public class GetAllCategoryUseCase {

    private CategoryDao dao;

    public GetAllCategoryUseCase(CategoryDao dao) {
        this.dao = dao;
    }

    public PageData<Category> apply(Integer page) {
        return dao.findAll(Utils.normalizePage(page));
    }

}
