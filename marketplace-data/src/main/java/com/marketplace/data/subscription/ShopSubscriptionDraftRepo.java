package com.marketplace.data.subscription;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopSubscriptionDraftRepo extends JpaRepository<ShopSubscriptionDraftEntity, Long> {

	void deleteByCreatedAtLessThan(long createdAt);
	
}
