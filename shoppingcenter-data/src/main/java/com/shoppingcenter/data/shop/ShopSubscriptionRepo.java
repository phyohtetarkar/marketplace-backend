package com.shoppingcenter.data.shop;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopSubscriptionRepo extends JpaRepository<ShopSubscriptionEntity, Long> {
	
	Optional<ShopSubscriptionEntity> findByShop_IdAndStatus(long shopId, ShopSubscriptionEntity.Status status);
	
	Optional<ShopSubscriptionEntity> findByShop_IdAndStatusAndStartAt(long shopId, ShopSubscriptionEntity.Status status, long startAt);

	Page<ShopSubscriptionEntity> findByShop_Id(long shopId, Pageable pageable);
}
