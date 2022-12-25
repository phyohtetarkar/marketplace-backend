package com.shoppingcenter.data.shoppingcart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItemEntity, CartItemEntity.Id> {

	List<CartItemEntity> findByUserId(String userId);

	void deleteByUserId(String userId);

	void deleteByProductId(long productId);
}
