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
        entity.setLft(category.getLft());
        entity.setRgt(category.getRgt());
        entity.setName(category.getName());
        entity.setFeatured(category.isFeatured());
        entity.setSlug(category.getSlug());
        entity.setImage(category.getImage());

        // if (entity.getId() == 0 || !Utils.equalsIgnoreCase(entity.getName(),
        // category.getName())) {
        // String prefix = category.getSlug().replaceAll("\\s+", "-").toLowerCase();
        // String slug = Utils.generateSlug(prefix, repo::existsBySlug);
        // entity.setSlug(slug);
        // }

        if (category.getCategoryId() != null) {
            entity.setCategory(repo.getReferenceById(category.getCategoryId()));
        }

        var result = repo.save(entity);
        
        result.setSlug(result.getSlug() + "-" + result.getId());
        
        return CategoryMapper.toDomainCompat(result, null);
    }

    @Override
    public void saveAll(List<Category> list) {
        var entities = list.stream().map(c -> {
            var entity = new CategoryEntity();
            entity.setId(c.getId());
            entity.setLft(c.getLft());
            entity.setRgt(c.getRgt());
            entity.setName(c.getName());
            entity.setFeatured(c.isFeatured());
            entity.setSlug(c.getSlug());
            entity.setImage(c.getImage());
            if (c.getCategoryId() != null) {
                entity.setCategory(repo.getReferenceById(c.getCategoryId()));
            }
            return entity;
        }).toList();

        repo.saveAll(entities);
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
