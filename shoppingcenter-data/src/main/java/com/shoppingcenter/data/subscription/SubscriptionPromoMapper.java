package com.shoppingcenter.data.subscription;

import com.shoppingcenter.domain.subscription.SubscriptionPromo;

public class SubscriptionPromoMapper {

	public static SubscriptionPromo toDomain(SubscriptionPromoEntity entity) {
		var promo = new SubscriptionPromo();
		promo.setId(entity.getId());
		promo.setCode(entity.getCode());
		promo.setValueType(entity.getValueType());
		promo.setValue(entity.getValue());
		promo.setMinConstraint(entity.getMinConstraint());
		promo.setUsed(entity.isUsed());
		promo.setExpiredAt(entity.getExpiredAt());
		promo.setCreatedAt(entity.getCreatedAt());
		return promo;
	}
	
}
