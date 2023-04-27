package com.shoppingcenter.data.category;

import java.util.Optional;

import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.search.product.CategoryDocument;

public class CategoryMapper {

    public static Category toDomain(CategoryEntity entity) {
        Category c = toDomainCompat(entity);
        if (entity.getCategory() != null) {
            c.setCategory(toDomainCompat(entity.getCategory()));
        }
        return c;
    }

    public static Category toDomainCompat(CategoryEntity entity) {
        Category c = new Category();
        c.setId(entity.getId());
        c.setLft(entity.getLft());
        c.setRgt(entity.getRgt());
        c.setName(entity.getName());
        c.setSlug(entity.getSlug());
        c.setFeatured(entity.isFeatured());
        c.setCategoryId(Optional.ofNullable(entity.getCategory()).map(CategoryEntity::getId).orElse(null));
        c.setCreatedAt(entity.getCreatedAt());
        c.setImage(entity.getImage());
        // if (StringUtils.hasText(entity.getImage())) {
        // c.setImage(baseUrl + "category/" + entity.getImage());
        // }
        return c;
    }

    public static CategoryDocument toDocument(Category category) {
        var document = new CategoryDocument();
        document.setId(category.getId());
        document.setName(category.getName());
        document.setSlug(category.getSlug());
        return document;
    }

    public static CategoryDocument toDocument(CategoryEntity category) {
        var document = new CategoryDocument();
        document.setId(category.getId());
        document.setName(category.getName());
        document.setSlug(category.getSlug());
        return document;
    }

}
