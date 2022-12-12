package com.shoppingcenter.data.product;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepo extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
	
	Optional<ProductEntity> findBySlug(String slug);
	
	Page<ProductEntity> findByShop_Id(long shopId, Pageable pageable);
	
	Page<ProductEntity> findByCategory_Id(long categoryId, Pageable pageable);
	
	Page<ProductEntity> findByBrand_Id(long brandId, Pageable pageable);
	
	void deleteByShop_Id(long shopId);
	
}
