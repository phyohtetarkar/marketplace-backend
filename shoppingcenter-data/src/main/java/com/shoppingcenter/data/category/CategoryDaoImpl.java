package com.shoppingcenter.data.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.category.view.CategoryImageView;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.common.AppProperties;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private CategoryRepo repo;

    @Autowired
    private AppProperties properties;

    @Override
    public Category save(Category category) {
        var entity = repo.findById(category.getId()).orElseGet(CategoryEntity::new);
        entity.setName(category.getName());
        entity.setFeatured(category.isFeatured());
        entity.setSlug(category.getSlug());
        
        if (category.getCategoryId() != null) {
            entity.setCategory(repo.getReferenceById(category.getCategoryId()));
        }

        var result = repo.save(entity);
        
        var slug = result.getSlug() + "-" + result.getId();
        
        repo.updateSlug(result.getId(), slug);     
        
        return CategoryMapper.toDomainCompat(result, null);
    }

    @Override
    public void saveLftRgt(List<Category> list) {
    	for (var c : list) {
    		var entity = repo.findById(c.getId()).orElse(null);
            
            if (entity == null) {
            	continue;
            }
            
            if (entity.getLft() == c.getLft() && entity.getRgt() == c.getRgt()) {
            	continue;
            }
            
            repo.updateLftRgt(entity.getId(), c.getLft(), c.getRgt());
    	}
    }
    
    @Override
    public void updateImage(int id, String image) {
    	repo.updateImage(id, image);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    @Override
    public boolean existsByCategoryId(int id) {
        return repo.existsByCategory_Id(id);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return repo.existsBySlug(slug);
    }

    @Override
    public String getCategoryImage(int id) {
        return repo.getCategoryById(id, CategoryImageView.class).map(CategoryImageView::getImage).orElse(null);
    }

    @Override
    public Category findById(int id) {
        return repo.findById(id).map(e -> CategoryMapper.toDomain(e, properties.getImageUrl())).orElse(null);
    }

    @Override
    public Category findBySlug(String slug) {
        return repo.findBySlug(slug).map(e -> CategoryMapper.toDomain(e, properties.getImageUrl())).orElse(null);
    }

    @Override
    public List<Category> findRootCategories() {
        return repo.findByCategoryNull().stream()
                .map(e -> CategoryMapper.toDomainCompat(e, properties.getImageUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> findAll() {
        return repo.findAll().stream()
                .map(e -> CategoryMapper.toDomain(e, properties.getImageUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public PageData<Category> findAll(int page) {
        Sort sort = Sort.by(Order.desc("createdAt"));
        PageRequest request = PageRequest.of(page, Constants.PAGE_SIZE, sort);

        Page<CategoryEntity> pageResult = repo.findAll(request);

        return PageDataMapper.map(pageResult, e -> CategoryMapper.toDomain(e, properties.getImageUrl()));
    }

}
