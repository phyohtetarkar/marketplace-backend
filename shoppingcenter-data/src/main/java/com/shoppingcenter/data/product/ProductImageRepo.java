package com.shoppingcenter.data.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepo extends JpaRepository<ProductImageEntity, ProductImageEntity.ID> {

	List<ProductImageEntity> findByProductId(long productId);

	void deleteByProductId(long productId);

}
