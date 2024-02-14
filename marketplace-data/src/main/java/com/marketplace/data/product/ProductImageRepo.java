package com.marketplace.data.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepo extends JpaRepository<ProductImageEntity, Long> {
	
	<T> Optional<T> getProductImageById(long id, Class<T> type);

	List<ProductImageEntity> findByProductId(long productId);

	void deleteByProductId(long productId);

}
