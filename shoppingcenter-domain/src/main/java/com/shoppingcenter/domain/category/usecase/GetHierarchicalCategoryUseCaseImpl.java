package com.shoppingcenter.domain.category.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;

public class GetHierarchicalCategoryUseCaseImpl implements GetHierarchicalCategoryUseCase {

    private CategoryDao dao;

    public GetHierarchicalCategoryUseCaseImpl(CategoryDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Category> apply() {
        var categories = dao.findAll();

        var result = new ArrayList<Category>();

        result.addAll(categories.stream()
                .filter(c -> c.getCategoryId() == null)
                .collect(Collectors.toList()));

        loadChildren(result, categories);

        return result;
    }

    private void loadChildren(List<Category> parents, List<Category> list) {

        for (Category category : parents) {
            List<Category> children = list.stream()
                    .filter(c -> Objects.equals(c.getCategoryId(), category.getId()))
                    .collect(Collectors.toList());

            if (children.isEmpty()) {
                continue;
            }

            category.setChildren(children);
            loadChildren(children, list);
        }
    }

}
