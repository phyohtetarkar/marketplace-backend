package com.shoppingcenter.data.shoppingcart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepo extends JpaRepository<CartItemEntity, CartItemEntity.ID> {

	List<CartItemEntity> findByUserId(long userId);

	void deleteByUserId(Long userId);

	void deleteByProductId(long productId);

	long countByUserId(long userId);

	@Modifying
	@Query("UPDATE CartItem c SET c.quantity = :quantity WHERE c.id = :id")
	void updateQuantity(@Param("id") CartItemEntity.ID id, @Param("quantity") int quantity);

}
