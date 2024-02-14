package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.subscription.SubscriptionPromo;
import com.marketplace.domain.subscription.SubscriptionPromoInput;
import com.marketplace.domain.subscription.dao.SubscriptionPromoDao;

@Component
public class CreateSubscriptionPromoUseCase {

	@Autowired
	private SubscriptionPromoDao dao;
	
	@Transactional
	public SubscriptionPromo apply(SubscriptionPromoInput values) {
		if (!Utils.hasText(values.getCode())) {
			throw new ApplicationException("Required promo code");
		}
		
		if (dao.existsByCode(values.getCode())) {
			throw new ApplicationException("Promo code already exists");
		}
		
		var promo = new SubscriptionPromo();
		promo.setCode(values.getCode());
		promo.setMinConstraint(values.getMinConstraint());
		promo.setValue(values.getValue());
		promo.setValueType(values.getValueType());
		promo.setExpiredAt(values.getExpiredAt());
		
		return dao.create(promo);
	}
	
}
