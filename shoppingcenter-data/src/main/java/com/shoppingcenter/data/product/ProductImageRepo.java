package com.shoppingcenter.data.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepo extends JpaRepository<ProductImageEntity, Long> {

	List<ProductImageEntity> findByProductId(long productId);

	void deleteByProduct_Id(long productId);

}
