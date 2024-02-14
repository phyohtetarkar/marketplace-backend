package com.marketplace.data.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.marketplace.data.category.view.CategoryWithNameView;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {

	<T> Optional<T> getCategoryById(int id, Class<T> type);

	Optional<CategoryEntity> findBySlug(String slug);

	long countByCategory_Id(int categoryId);

	boolean existsByCategory_Id(int categoryId);

	boolean existsBySlug(String slug);
	
	boolean existsByIdNotAndSlug(int id, String slug);
	
	List<CategoryEntity> findByCategoryNull();
	
	List<CategoryEntity> findByCategoryId(int categoryId);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Category c SET c.image = :image WHERE c.id = :id")
	void updateImage(@Param("id") int id, @Param("image") String image);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Category c SET c.slug = :slug WHERE c.id = :id")
	void updateSlug(@Param("id") int id, @Param("slug") String slug);
	
	@Modifying
	@Query("UPDATE Category c SET c.lft = :lft, c.rgt = :rgt WHERE c.id = :id")
	void updateLftRgt(@Param("id") int id, @Param("lft") int lft, @Param("rgt") int rgt);
	
	@Query("SELECT c as category, n as name from Category c LEFT JOIN CategoryName n ON n.id.categoryId = c.id AND n.id.lang = :lang WHERE c.category.id IS NULL")
	List<CategoryWithNameView> getRootCategoriesByLang(@Param("lang") String lang);
	
	@Query("SELECT c as category, n as name from Category c LEFT JOIN CategoryName n ON n.id.categoryId = c.id AND n.id.lang = :lang")
	List<CategoryWithNameView> getCategoriesByLang(@Param("lang") String lang);
	
	@Query("SELECT c as category, n as name from Category c LEFT JOIN CategoryName n ON n.id.categoryId = c.id AND n.id.lang = :lang")
	Page<CategoryWithNameView> getCategoriesByLang(@Param("lang") String lang, Pageable pageable);
}
