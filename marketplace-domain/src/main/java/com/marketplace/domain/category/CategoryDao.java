package com.marketplace.domain.category;

import java.util.List;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;

public interface CategoryDao {

    Category save(CategoryInput values);

    void saveLftRgt(List<Category> list);
    
    void updateImage(int id, String image);

    void delete(int id);

    boolean existsById(int id);

    boolean existsByCategory(int id);

    boolean existsBySlug(String slug);
    
    boolean existsByIdNotAndSlug(int id, String slug);

    String getCategoryImage(int id);

    Category findById(int id);

    Category findBySlug(String slug);

    List<Category> findRootCategories();

    List<Category> findAll(boolean withNames);
    
    List<Category> findByCategory(int categoryId);
    
    PageData<Category> findAll(PageQuery pageQuery);

}
