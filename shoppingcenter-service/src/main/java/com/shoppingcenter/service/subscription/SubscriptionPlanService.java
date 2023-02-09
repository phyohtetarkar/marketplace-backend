package com.shoppingcenter.service.subscription;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.subscription.model.SubscriptionPlan;

public interface SubscriptionPlanService {
    void save(SubscriptionPlan plan);

    void delete(long id);

    SubscriptionPlan findById(long id);

    PageData<SubscriptionPlan> findAll(Integer page);
}
