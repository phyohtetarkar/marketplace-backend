package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.category.Category;

public interface GetAllCategoryUseCase {

    PageData<Category> apply(Integer page);

}
