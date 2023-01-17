package com.shoppingcenter.data.shoppingcart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItemEntity, String> {

	List<CartItemEntity> findByUser_Id(String userId);

	void deleteByUser_Id(String userId);

	void deleteByProduct_Id(long productId);
}
