package com.shoppingcenter.data.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductRepo extends JpaRepository<FavoriteProductEntity, Long> {

	Page<FavoriteProductEntity> findByUser_Id(String userId, Pageable pageable);

	void deleteByUser_Id(String userId);

	void deleteByProduct_Id(long productId);

}
