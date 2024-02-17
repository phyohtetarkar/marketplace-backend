package com.marketplace.data.category;

import com.marketplace.data.AuditMapper;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryName;

public interface CategoryMapper {

	public static Category toDomain(CategoryEntity entity) {
		var c = convert(entity);
		if (entity.getCategory() != null) {
			c.setCategory(toDomain(entity.getCategory()));
		}
		var names = entity.getNames();
		if (names != null) {
			c.setNames(entity.getNames().stream().map(CategoryMapper::toName).toList());
		}
		return c;
	}
	
	public static Category toDomain(CategoryEntity entity, boolean withNames) {
		var c = convert(entity);
		if (withNames) {
			var names = entity.getNames();
			if (names != null) {
				c.setNames(entity.getNames().stream().map(CategoryMapper::toName).toList());
			}
		}
		if (entity.getCategory() != null) {
			c.setCategory(convert(entity.getCategory()));
		}
		return c;
	}

	public static Category toDomainCompat(CategoryEntity entity) {
		var c = convert(entity);
		var names = entity.getNames();
		if (names != null) {
			c.setNames(entity.getNames().stream().map(CategoryMapper::toName).toList());
		}
		return c;
	}

	public static CategoryName toName(CategoryNameEntity entity) {
		var name = new CategoryName();
		if (entity != null) {
			name.setLang(entity.getLang());
			name.setName(entity.getName());
		}
		return name;
	}

	private static Category convert(CategoryEntity entity) {
		var c = new Category();
		c.setId(entity.getId());
		c.setLft(entity.getLft());
		c.setRgt(entity.getRgt());
		c.setSlug(entity.getSlug());
		c.setFeatured(entity.isFeatured());
		c.setImage(entity.getImage());
		c.setAudit(AuditMapper.from(entity));
		return c;
	}

}
