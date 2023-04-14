package com.shoppingcenter.data.product.variant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepo extends JpaRepository<ProductVariantEntity, Long> {

	List<ProductVariantEntity> findByProductId(long productId);

	void deleteByProductId(long productId);
}
