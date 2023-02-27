package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;

public class GetAllCategoryUseCaseImpl implements GetAllCategoryUseCase {

    private CategoryDao dao;

    public GetAllCategoryUseCaseImpl(CategoryDao dao) {
        this.dao = dao;
    }

    @Override
    public PageData<Category> apply(Integer page) {
        return dao.findAll(Utils.normalizePage(page));
    }

}
