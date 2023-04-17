package com.shoppingcenter.domain.category;

import java.util.List;

import com.shoppingcenter.domain.PageData;

public interface CategoryDao {

    Category save(Category category);

    void saveAll(List<Category> list);
    
    void updateImage(int id, String image);

    void delete(int id);

    boolean existsById(int id);

    boolean existsByCategoryId(int id);

    boolean existsBySlug(String slug);

    String getCategoryImage(int id);

    Category findById(int id);

    Category findBySlug(String slug);

    List<Category> findRootCategories();

    List<Category> findAll();

    PageData<Category> findAll(int page);

}
