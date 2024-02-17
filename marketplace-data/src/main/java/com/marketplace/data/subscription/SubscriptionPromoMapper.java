package com.marketplace.data.subscription;

import com.marketplace.data.AuditMapper;
import com.marketplace.domain.subscription.SubscriptionPromo;

public interface SubscriptionPromoMapper {

	public static SubscriptionPromo toDomain(SubscriptionPromoEntity entity) {
		var promo = new SubscriptionPromo();
		promo.setId(entity.getId());
		promo.setCode(entity.getCode());
		promo.setValueType(entity.getValueType());
		promo.setValue(entity.getValue());
		promo.setMinConstraint(entity.getMinConstraint());
		promo.setUsed(entity.isUsed());
		promo.setExpiredAt(entity.getExpiredAt());
		promo.setAudit(AuditMapper.from(entity));
		return promo;
	}
	
}
