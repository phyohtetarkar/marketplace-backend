package com.marketplace.data.product;

import java.math.BigDecimal;
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

import com.marketplace.data.discount.DiscountEntity;
import com.marketplace.data.product.view.ProductBrandView;
import com.marketplace.domain.product.Product;

public interface ProductRepo extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

	<T> Optional<T> getProductById(long id, Class<T> type);

	Optional<ProductEntity> findBySlug(String slug);

	Page<ProductEntity> findByShop_Id(long shopId, Pageable pageable);

	Page<ProductEntity> findByShop_IdAndDiscount_Id(long shopId, long discountId, Pageable pageable);

	Page<ProductEntity> findByIdNotAndCategoryIdAndStatusAndDeletedFalse(long id, int categoryId, Product.Status status,
			Pageable pageable);
	
	List<ProductEntity> findTop12ByFeaturedTrueAndDeletedFalseAndStatusAndShop_ExpiredAtGreaterThanOrderByCreatedAtDesc(Product.Status staus, long expiredAt);
	
	List<ProductEntity> findTop12ByDeletedFalseAndDiscountNotNullAndStatusAndShop_ExpiredAtGreaterThanOrderByCreatedAtDesc(Product.Status staus, long expiredAt);

	long countByDiscount_Id(long discountId);

	long countByShop_IdAndDeletedFalse(long shopId);
	
	long countByDeletedFalse();

	void deleteByShop_Id(long shopId);

	boolean existsByCategory_IdAndDeletedFalse(int categoryId);
	
	boolean existsByIdAndShop_IdAndDeletedFalse(long id, long shopId);

	boolean existsByIdAndDeletedFalse(long id);
	
	boolean existsBySlug(String slug);
	
	boolean existsBySlugAndDeletedFalse(String slug);

	boolean existsByDiscount_Id(long discountId);
	
	boolean existsByIdNotAndSlug(long id, String slug);

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

	@Modifying
	@Query("UPDATE Product p SET p.slug = :slug WHERE p.id = :id")
	void updateSlug(@Param("id") long id, @Param("slug") String slug);

	@Modifying
	@Query("UPDATE Product p SET p.thumbnail = :thumbnail WHERE p.id = :id")
	void updateThumbnail(@Param("id") long id, @Param("thumbnail") String thumbnail);

	@Modifying
	@Query("UPDATE Product p SET p.status = :status WHERE p.id = :id")
	void updateStatus(@Param("id") long id, @Param("status") Product.Status status);
	
	@Modifying
	@Query("UPDATE Product p SET p.deleted = :deleted WHERE p.id = :id")
	void updateDeleted(@Param("id") long id, @Param("deleted") boolean deleted);
	
	@Modifying
	@Query("UPDATE Product p SET p.price = :price WHERE p.id = :id")
	void updatePrice(@Param("id") long id, @Param("price") BigDecimal price);
	
	@Modifying
	@Query("UPDATE Product p SET p.description = :description WHERE p.id = :id")
	void updateDescription(@Param("id") long id, @Param("description") String description);

	@Query("SELECT p from Product p WHERE (LOWER(p.name) LIKE :name or LOWER(p.brand) LIKE :brand) AND p.deleted = false")
	List<ProductEntity> findProductHints(@Param("name") String name, @Param("brand") String brand, Pageable pageable);

	// @Query("SELECT DISTINCT p.brand as brand from Product p JOIN p.categories pc
	// WHERE pc = :categoryId AND p.disabled = false ORDER BY p.brand ASC")
	@Query("SELECT DISTINCT p.brand as brand from Product p WHERE p.category.lft >= :lft AND p.category.rgt <= :rgt AND p.deleted = false ORDER BY p.brand ASC")
	List<ProductBrandView> findDistinctBrandsByCategory(@Param("lft") int lft, @Param("rgt") int rgt);
	
	@Query("SELECT DISTINCT p.brand as brand from Product p WHERE lower(p.name) LIKE lower(:q) AND p.deleted = false ORDER BY p.brand ASC")
	List<ProductBrandView> findDistinctBrandsByNameLike(@Param("q") String q);
	
	@Query("SELECT MAX(p.price) from Product p WHERE p.category.lft >= :lft AND p.category.rgt <= :rgt AND p.deleted = false")
	BigDecimal getMaxPriceByCategory(@Param("lft") int lft, @Param("rgt") int rgt);
	
	@Query("SELECT MAX(p.price) from Product p WHERE lower(p.name) LIKE lower(:q) AND p.deleted = false")
	BigDecimal getMaxPriceByNameLike(@Param("q") String q);

}
