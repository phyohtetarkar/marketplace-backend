package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.subscription.SubscriptionPromo;
import com.marketplace.domain.subscription.dao.SubscriptionPromoDao;

@Component
public class GetSubscriptionPromoByIdUseCase {

	@Autowired
	private SubscriptionPromoDao dao;
	
	public SubscriptionPromo apply(long id) {
		var promo = dao.findById(id);
		if (promo == null) {
			throw ApplicationException.notFound("Promo code not found");
		}
		
		return promo;
	}
	
}
