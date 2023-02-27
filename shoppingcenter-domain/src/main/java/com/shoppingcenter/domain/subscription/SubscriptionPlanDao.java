package com.shoppingcenter.domain.subscription;

import com.shoppingcenter.domain.PageData;

public interface SubscriptionPlanDao {

    void save(SubscriptionPlan plan);

    void delete(long id);

    SubscriptionPlan findById(long id);

    PageData<SubscriptionPlan> findAll(int page);

}
