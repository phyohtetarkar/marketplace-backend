package com.shoppingcenter.data.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepo extends JpaRepository<OrderItemEntity, Long> {
	
	List<OrderItemEntity> findByOrderId(long orderId);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE OrderItem item SET item.product = NULL WHERE item.product.id = :productId")
	void removeProductRelation(@Param("productId") long productId);
	
}
