package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.category.Category;

public interface GetCategoryByIdUseCase {

    Category apply(int id);

}
