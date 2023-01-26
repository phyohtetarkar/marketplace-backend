package com.shoppingcenter.data.shoppingcart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepo extends JpaRepository<CartItemEntity, Long> {

	List<CartItemEntity> findByUser_Id(String userId);

	void deleteByUser_Id(String userId);

	void deleteByProduct_Id(long productId);

	@Modifying
	@Query("UPDATE CartItem c SET c.quantity = :quantity WHERE c.id = :id")
	void updateQuantity(@Param("id") long id, @Param("quantity") int quantity);
}
