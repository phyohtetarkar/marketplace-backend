package com.marketplace.domain.subscription.dao;

import com.marketplace.domain.subscription.ShopSubscriptionDraft;

public interface ShopSubscriptionDraftDao {

	ShopSubscriptionDraft create(ShopSubscriptionDraft values);
	
	void deleteByCreatedAtLessThan(long createdAt);
	
	boolean existsById(long id);
	
	ShopSubscriptionDraft findById(long id);
	
}
