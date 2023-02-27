package com.shoppingcenter.domain.category.usecase;

import java.util.List;

import com.shoppingcenter.domain.category.Category;

public interface GetRootCategoriesUseCase {
    List<Category> apply();
}
