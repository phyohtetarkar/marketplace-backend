package com.shoppingcenter.data.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {

	Optional<CategoryEntity> findBySlug(String slug);

	List<CategoryEntity> findByCategoryNull();

	List<CategoryEntity> findByCategory_Id(int cateogryId);

	long countByCategory_Id(int categoryId);
}
