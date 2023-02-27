package com.shoppingcenter.data.category;

import java.util.Optional;

import org.springframework.util.StringUtils;

import com.shoppingcenter.domain.category.Category;

public class CategoryMapper {

    public static Category toDomain(CategoryEntity entity, String baseUrl) {
        Category c = toDomainCompat(entity, baseUrl);
        if (entity.getCategory() != null) {
            c.setCategory(toDomain(entity.getCategory(), baseUrl));
        }
        return c;
    }

    // public static Category toDomainComplete(CategoryEntity entity, String
    // baseUrl) {
    // Category c = toDomainCompat(entity, baseUrl);

    // if (entity.getCategories() != null && !entity.getCategories().isEmpty()) {
    // c.setChildren(
    // entity.getCategories().stream().map(e -> toDomainComplete(e, baseUrl))
    // .collect(Collectors.toList()));
    // }

    // return c;
    // }

    public static Category toDomainCompat(CategoryEntity entity, String baseUrl) {
        Category c = new Category();
        c.setId(entity.getId());
        c.setName(entity.getName());
        c.setSlug(entity.getSlug());
        c.setFeatured(entity.isFeatured());
        c.setCategoryId(Optional.ofNullable(entity.getCategory()).map(CategoryEntity::getId).orElse(null));
        c.setCreatedAt(entity.getCreatedAt());
        c.setImage(entity.getImage());
        if (StringUtils.hasText(entity.getImage())) {
            c.setImageUrl(baseUrl + "category/" + entity.getImage());
        }
        return c;
    }

}
