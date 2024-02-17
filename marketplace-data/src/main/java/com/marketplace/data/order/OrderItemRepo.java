package com.marketplace.data.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepo extends JpaRepository<OrderItemEntity, Long> {
	
	List<OrderItemEntity> findByOrderId(long orderId);
	
	long countByOrderIdAndCancelledFalse(long orderId);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE OrderItem o SET o.cancelled = :cancelled WHERE o.id = :id")
	void updateCancelled(@Param("id") long id, @Param("cancelled") boolean cancelled);
	
}
