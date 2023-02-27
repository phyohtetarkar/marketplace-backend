package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.subscription.SubscriptionPlan;

public interface GetSubscriptionPlanByIdUseCase {

    SubscriptionPlan apply(long id);

}
