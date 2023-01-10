package com.shoppingcenter.data.variant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepo extends JpaRepository<ProductVariantEntity, ProductVariantEntity.ID> {

	List<ProductVariantEntity> findByProductId(long productId);

	void deleteByProductId(long productId);
}
