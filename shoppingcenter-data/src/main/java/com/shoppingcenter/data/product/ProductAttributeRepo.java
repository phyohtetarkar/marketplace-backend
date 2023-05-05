package com.shoppingcenter.data.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepo extends JpaRepository<ProductAttributeEntity, Long> {

	List<ProductAttributeEntity> findByProductId(long productId);
	
}
