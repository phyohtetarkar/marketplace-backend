package com.marketplace.domain.category.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDao;

@Component
public class GetHierarchicalCategoryUseCase {

	@Autowired
    private CategoryDao dao;

	@Transactional(readOnly = true)
    public List<Category> apply(boolean withNames) {
        var categories = dao.findAll(withNames);

        var result = new ArrayList<Category>();

        result.addAll(categories.stream()
                .filter(c -> c.getCategory() == null)
//                .sorted((f, s) -> f.getName().compareTo(s.getName()))
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
//                    .sorted((f, s) -> f.getName().compareTo(s.getName()))
                    .collect(Collectors.toList());

            if (children.isEmpty()) {
                continue;
            }

            category.setChildren(children);
            loadChildren(children, list);
            
        }
    }

}
