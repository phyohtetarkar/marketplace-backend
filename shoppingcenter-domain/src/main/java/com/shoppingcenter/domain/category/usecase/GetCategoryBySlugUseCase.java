package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.category.Category;

public interface GetCategoryBySlugUseCase {

    Category apply(String slug);

}
