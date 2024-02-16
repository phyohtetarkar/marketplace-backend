package com.marketplace.data.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryNameRepo extends JpaRepository<CategoryNameEntity, CategoryNameEntity.ID> {

	void deleteByCategoryId(long categoryId);
	
}
