package com.shoppingcenter.data.product;

import java.util.Collection;
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

	Page<ProductEntity> findByStatus(String status, Pageable pageable);

	Page<ProductEntity> findByShop_Id(long shopId, Pageable pageable);

	Page<ProductEntity> findByCategory_Id(int categoryId, Pageable pageable);

	Page<ProductEntity> findByShop_IdAndDiscount_Id(long shopId, long discountId, Pageable pageable);

	List<ProductEntity> findTop8ByNameLikeOrBrandLikeAndStatus(String name, String brand, String status);

	List<ProductEntity> findTop8ByFeaturedTrueAndStatus(String status);

	Page<ProductEntity> findByIdNotAndCategory_IdAndStatus(long id, int categoryId, String status, Pageable pageable);

	long countByDiscount_Id(long discountId);

	long countByShop_Id(long shopId);

	long countByIdNotAndCategory_IdAndStatus(long id, int categoryId, String status);

	void deleteByShop_Id(long shopId);

	boolean existsByCategory_Id(int categoryId);

	boolean existsBySlug(String slug);

	boolean existsBySlugAndStatus(String slug, String status);

	boolean existsByDiscount_Id(long discountId);

	boolean existsByIdAndStatus(long id, String status);

	@Modifying
	@Query("UPDATE Product p SET p.featured = :featured WHERE p.id = :id")
	void updateFeatured(@Param("id") long id, @Param("featured") boolean featured);

	@Modifying
	@Query("UPDATE Product p SET p.discount = :discount WHERE p.id IN :productIds")
	void applyDiscounts(@Param("discount") DiscountEntity discount, @Param("productIds") Collection<Long> productIds);

	@Modifying
	@Query("UPDATE Product p SET p.discount = :discount WHERE p.shop.id = :shopId")
	void applyDiscountAll(@Param("discount") DiscountEntity discount, @Param("shopId") long shopId);

	@Modifying
	@Query("UPDATE Product p SET p.discount = NULL WHERE p.id = :productId")
	void removeDiscount(@Param("productId") long productId);

	@Modifying
	@Query("UPDATE Product p SET p.discount = NULL WHERE p.discount.id = :discountId")
	void removeDiscountAll(@Param("discountId") long discountId);

	@Modifying
	@Query("UPDATE Product p SET p.discount = NULL WHERE p.shop.id = :shopId AND p.discount.id = :discountId")
	void removeDiscountAll(@Param("shopId") long shopId, @Param("discountId") long discountId);

	@Query("SELECT p from Product p WHERE (LOWER(p.name) LIKE :name or LOWER(p.brand) LIKE :brand) AND p.status = 'PUBLISHED'")
	List<ProductEntity> findProductHints(@Param("name") String name, @Param("brand") String brand, Pageable pageable);

	@Query("SELECT DISTINCT p.brand as brand from Product p WHERE p.category.slug = :categorySlug AND p.status = 'PUBLISHED' ORDER BY p.brand ASC")
	List<ProductBrandView> findDistinctBrands(@Param("categorySlug") String categorySlug);

}
