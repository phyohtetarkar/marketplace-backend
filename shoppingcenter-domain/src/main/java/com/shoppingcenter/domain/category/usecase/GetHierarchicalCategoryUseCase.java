package com.shoppingcenter.domain.category.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;

public class GetHierarchicalCategoryUseCase {

    private CategoryDao dao;

    public GetHierarchicalCategoryUseCase(CategoryDao dao) {
        this.dao = dao;
    }

    public List<Category> apply() {
        var categories = dao.findAll();

        var result = new ArrayList<Category>();

        result.addAll(categories.stream()
                .filter(c -> c.getCategory() == null)
                .sorted((f, s) -> f.getName().compareTo(s.getName()))
                .collect(Collectors.toList()));

        loadChildren(result, categories);

        return result;
    }

    private void loadChildren(List<Category> parents, List<Category> list) {

        for (Category category : parents) {
            List<Category> children = list.stream()
                    .filter(c -> {
                        if (c.getCategory() == null) {
                            return false;
                        }

                        return c.getCategory().getId() == category.getId();
                    })
                    .sorted((f, s) -> f.getName().compareTo(s.getName()))
                    .collect(Collectors.toList());

            if (children.isEmpty()) {
                continue;
            }

            category.setChildren(children);
            loadChildren(children, list);
        }
    }

}
