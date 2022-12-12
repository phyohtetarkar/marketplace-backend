package com.shoppingcenter.data.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepo extends JpaRepository<ProductVariantEntity, Long> {
	
	List<ProductVariantEntity> findByProduct_Id(long productId);

	void deleteByProduct_Id(long productId);
}
