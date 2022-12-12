package com.shoppingcenter.data.subscription;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPromoRepo extends JpaRepository<SubscriptionPromoEntity, Long> {
	
	Optional<SubscriptionPromoEntity> findByCode(String code);
	
	boolean existsByCode(String code);
	
}
