package com.marketplace.data.subscription;

import com.marketplace.data.AuditMapper;
import com.marketplace.domain.subscription.SubscriptionPlan;

public interface SubscriptionPlanMapper {

    public static SubscriptionPlan toDomain(SubscriptionPlanEntity entity) {
        var sp = new SubscriptionPlan();
        sp.setId(entity.getId());
        sp.setTitle(entity.getTitle());
        sp.setDuration(entity.getDuration());
        sp.setPromoUsable(entity.isPromoUsable());
        sp.setPrice(entity.getPrice());
        sp.setAudit(AuditMapper.from(entity));
        return sp;
    }

}
