package com.marketplace.domain.subscription.dao;

import java.util.List;

import com.marketplace.domain.subscription.SubscriptionPlan;
import com.marketplace.domain.subscription.SubscriptionPlanInput;

public interface SubscriptionPlanDao {

	SubscriptionPlan save(SubscriptionPlanInput plan);

    void delete(long id);

    SubscriptionPlan findById(long id);

    List<SubscriptionPlan> findAll();

}
