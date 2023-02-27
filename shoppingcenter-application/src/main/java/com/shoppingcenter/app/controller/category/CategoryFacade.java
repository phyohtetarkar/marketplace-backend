package com.shoppingcenter.app.controller.category;

import java.util.List;

import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryEditDTO;
import com.shoppingcenter.domain.PageData;

public interface CategoryFacade {

    void save(CategoryEditDTO category);

    void delete(int id);

    CategoryDTO findById(int id);

    CategoryDTO findBySlug(String slug);

    boolean existsBySlug(String slug, Integer excludeId);

    List<CategoryDTO> findHierarchical();

    List<CategoryDTO> findRootCategories();

    PageData<CategoryDTO> findAll(Integer page);

}
