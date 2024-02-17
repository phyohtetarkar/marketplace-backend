package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.subscription.SubscriptionPlan;
import com.marketplace.domain.subscription.dao.SubscriptionPlanDao;

@Component
public class GetSubscriptionPlanByIdUseCase {

	@Autowired
    private SubscriptionPlanDao dao;

    public SubscriptionPlan apply(long id) {
        var plan = dao.findById(id);
        if (plan == null) {
            throw ApplicationException.notFound("Subscription plan not found");
        }
        return plan;
    }

}
