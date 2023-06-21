package com.shoppingcenter.domain.subscription;

import com.shoppingcenter.domain.PageData;

public interface SubscriptionPromoDao {

	SubscriptionPromo create(SubscriptionPromo promo);
	
	SubscriptionPromo update(SubscriptionPromo promo);
	
	void updateUsed(long id, boolean used);
	
	void deleteById(long id);
	
	boolean existsByCode(String code);
	
	boolean existsById(long id);
	
	SubscriptionPromo findById(long id);
	
	SubscriptionPromo findByCode(String code);
	
	PageData<SubscriptionPromo> findAll(SubscriptionPromoQuery query);
	
}
