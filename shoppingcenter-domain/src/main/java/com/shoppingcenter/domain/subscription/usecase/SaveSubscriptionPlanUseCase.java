package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.subscription.SubscriptionPlan;

public interface SaveSubscriptionPlanUseCase {
    void apply(SubscriptionPlan plan);
}
