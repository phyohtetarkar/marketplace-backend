package com.shoppingcenter.domain.subscription;

import java.util.List;

public interface SubscriptionPlanDao {

    void save(SubscriptionPlan plan);

    void delete(long id);

    SubscriptionPlan findById(long id);

    List<SubscriptionPlan> findAll();

}
