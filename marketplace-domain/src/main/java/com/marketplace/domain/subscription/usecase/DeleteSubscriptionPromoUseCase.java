package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

import com.marketplace.domain.subscription.dao.SubscriptionPromoDao;

@Component
public class DeleteSubscriptionPromoUseCase {

	@Autowired
	private SubscriptionPromoDao dao;
	
	public void apply(long id) {
		if (!dao.existsById(id)) {
			throw new ApplicationContextException("Promo code not found");
		}
		dao.deleteById(id);
	}
	
}
