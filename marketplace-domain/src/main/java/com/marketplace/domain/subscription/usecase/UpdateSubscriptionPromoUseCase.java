package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.subscription.SubscriptionPromo;
import com.marketplace.domain.subscription.SubscriptionPromoInput;
import com.marketplace.domain.subscription.dao.SubscriptionPromoDao;

@Component
public class UpdateSubscriptionPromoUseCase {

	@Autowired
	private SubscriptionPromoDao dao;
	
	@Transactional
	public SubscriptionPromo apply(SubscriptionPromoInput values) {
		var promo = dao.findById(values.getId());
		
		if (promo == null) {
			throw new ApplicationException("Promo code not found");
		}
		
		if (promo.isUsed()) {
			throw new ApplicationException("Cannot update used promo code");
		}
		
		promo.setMinConstraint(values.getMinConstraint());
		promo.setValue(values.getValue());
		promo.setValueType(values.getValueType());
		promo.setExpiredAt(values.getExpiredAt());
		
		return dao.update(promo);
	}
	
}
