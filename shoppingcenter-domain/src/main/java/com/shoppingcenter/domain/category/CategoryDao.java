package com.shoppingcenter.domain.category;

import java.util.List;

import com.shoppingcenter.domain.PageData;

public interface CategoryDao {

    void save(Category category);

    void delete(int id);

    boolean existsById(int id);

    boolean existsByCategoryId(int id);

    boolean existsBySlug(String slug);

    Category findById(int id);

    Category findBySlug(String slug);

    List<Category> findRootCategories();

    List<Category> findAll();

    PageData<Category> findAll(int page);

}
