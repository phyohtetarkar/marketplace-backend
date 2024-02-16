package com.marketplace.data.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusHistoryRepo extends JpaRepository<OrderStatusHistoryEntity, Long> {

}
