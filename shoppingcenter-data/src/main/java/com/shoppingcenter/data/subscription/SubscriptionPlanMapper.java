package com.shoppingcenter.data.subscription;

import com.shoppingcenter.domain.subscription.SubscriptionPlan;

public class SubscriptionPlanMapper {

    public static SubscriptionPlan toDomain(SubscriptionPlanEntity entity) {
        var sp = new SubscriptionPlan();
        sp.setId(entity.getId());
        sp.setTitle(entity.getTitle());
        sp.setDuration(entity.getDuration());
        sp.setPromoUsable(entity.isPromoUsable());
        sp.setPrice(entity.getPrice());
        sp.setCreatedAt(entity.getCreatedAt());
        return sp;
    }

}
