package com.marketplace.data.shoppingcart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItemEntity, CartItemEntity.ID> {

	List<CartItemEntity> findById_UserId(long userId);
	
	void deleteById_UserId(long userId);

	void deleteByProductId(long productId);
	
	void deleteByProduct_Shop_Id(long shopId);

	long countById_UserId(long userId);

}
