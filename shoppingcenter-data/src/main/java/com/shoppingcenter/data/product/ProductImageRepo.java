package com.shoppingcenter.data.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepo extends JpaRepository<ProductImageEntity, String> {

	List<ProductImageEntity> findByProduct_Id(long productId);

	void deleteByProduct_Id(long productId);

}
