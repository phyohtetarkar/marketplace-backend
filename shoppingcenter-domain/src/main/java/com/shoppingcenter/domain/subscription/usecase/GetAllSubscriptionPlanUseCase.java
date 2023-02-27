package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.subscription.SubscriptionPlan;

public interface GetAllSubscriptionPlanUseCase {

    PageData<SubscriptionPlan> apply(Integer page);

}
