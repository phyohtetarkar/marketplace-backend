package com.shoppingcenter.data.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductRepo extends JpaRepository<FavoriteProductEntity, FavoriteProductEntity.ID> {

	Page<FavoriteProductEntity> findByUserId(String userId, Pageable pageable);

	void deleteByUserId(String userId);

	void deleteByProductId(long productId);

}
