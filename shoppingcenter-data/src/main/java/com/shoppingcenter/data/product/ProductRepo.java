package com.shoppingcenter.data.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.data.product.view.ProductBrandView;

public interface ProductRepo extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

	Optional<ProductEntity> findBySlug(String slug);

	Page<ProductEntity> findByShop_Id(long shopId, Pageable pageable);

	Page<ProductEntity> findByCategory_Id(int categoryId, Pageable pageable);

	List<ProductEntity> findTop8ByNameLikeOrBrandLikeAndStatus(String name, String brand, String status);

	List<ProductEntity> findTop8ByFeaturedTrueAndStatus(String status);

	long countByDiscount(DiscountEntity entity);

	void deleteByShop_Id(long shopId);

	boolean existsByCategory_Id(int categoryId);

	boolean existsBySlug(String slug);

	boolean existsByDiscount(DiscountEntity entity);

	@Modifying
	@Query("UPDATE Product p SET p.featured = :featured WHERE p.id = :id")
	void updateFeatured(@Param("id") long id, @Param("featured") boolean featured);

	@Query("SELECT p from Product p WHERE (LOWER(p.name) LIKE :name or LOWER(p.brand) LIKE :brand) AND p.status = 'PUBLISHED'")
	List<ProductEntity> findProductHints(@Param("name") String name, @Param("brand") String brand, Pageable pageable);

	@Query("SELECT DISTINCT p.brand as brand from Product p WHERE p.category.id = :categoryId AND p.status = 'PUBLISHED' ORDER BY p.brand ASC")
	List<ProductBrandView> findDistinctBrands(@Param("categoryId") int categoryId);

}
