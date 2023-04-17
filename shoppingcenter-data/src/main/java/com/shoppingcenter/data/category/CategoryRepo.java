package com.shoppingcenter.data.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {

	<T> Optional<T> getCategoryById(int id, Class<T> type);

	Optional<CategoryEntity> findBySlug(String slug);

	List<CategoryEntity> findByCategoryNull();

	List<CategoryEntity> findByCategory_Id(int cateogryId);

	long countByCategory_Id(int categoryId);

	boolean existsByCategory_Id(int categoryId);

	boolean existsBySlug(String slug);
	
	@Modifying
	@Query("UPDATE Category c SET c.image = :image WHERE c.id = :id")
	void updateImage(@Param("id") int id, @Param("image") String image);
}
