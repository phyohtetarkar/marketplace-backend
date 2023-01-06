package com.shoppingcenter.data.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepo extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

	Optional<ProductEntity> findBySlug(String slug);

	Page<ProductEntity> findByShop_Id(long shopId, Pageable pageable);

	Page<ProductEntity> findByCategory_Id(int categoryId, Pageable pageable);

	List<ProductEntity> findTop8ByNameLikeOrBrandLike(String name, String brand);

	long countByDiscount_Id(long discountId);

	void deleteByShop_Id(long shopId);

	boolean existsByCategory_Id(int categoryId);

	boolean existsBySlug(String slug);

	boolean existsByDiscount_id(long discountId);

}
