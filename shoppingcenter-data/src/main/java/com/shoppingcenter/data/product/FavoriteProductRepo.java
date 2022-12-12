package com.shoppingcenter.data.product;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductRepo extends JpaRepository<FavoriteProductEntity, FavoriteProductEntity.Id> {
	
	Page<FavoriteProductEntity> findByUserId(String userId);
	
	void deleteByUserId(String userId);
	
	void deleteByProductId(long productId);

}
