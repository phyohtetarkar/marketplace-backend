package com.marketplace.domain.subscription.dao;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.subscription.SubscriptionPromo;

public interface SubscriptionPromoDao {

	SubscriptionPromo create(SubscriptionPromo promo);
	
	SubscriptionPromo update(SubscriptionPromo promo);
	
	void updateUsed(long id, boolean used);
	
	void deleteById(long id);
	
	boolean existsByCode(String code);
	
	boolean existsById(long id);
	
	SubscriptionPromo findById(long id);
	
	SubscriptionPromo findByCode(String code);
	
	PageData<SubscriptionPromo> findAvailable(PageQuery pageQuery);
	
	PageData<SubscriptionPromo> findUsed(PageQuery pageQuery);
	
	PageData<SubscriptionPromo> findExpired(PageQuery pageQuery);
	
	PageData<SubscriptionPromo> findAll(PageQuery pageQuery);
	
}
