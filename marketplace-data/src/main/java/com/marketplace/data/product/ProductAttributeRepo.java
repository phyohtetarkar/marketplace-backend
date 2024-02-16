package com.marketplace.data.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepo extends JpaRepository<ProductAttributeEntity, ProductAttributeEntity.ID> {

	List<ProductAttributeEntity> findByProductId(long productId);
	
}
