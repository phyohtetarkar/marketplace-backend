package com.marketplace.data.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductRepo extends JpaRepository<FavoriteProductEntity, FavoriteProductEntity.ID> {

	Page<FavoriteProductEntity> findByUserId(long userId, Pageable pageable);

	void deleteByUserId(long userId);

	void deleteByProductId(long productId);
	
	void deleteByProduct_Shop_Id(long shopId);
	
	long countByUserId(long userId);

}
