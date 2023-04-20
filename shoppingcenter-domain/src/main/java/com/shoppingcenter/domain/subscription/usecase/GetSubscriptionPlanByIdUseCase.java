package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.subscription.SubscriptionPlan;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;

public class GetSubscriptionPlanByIdUseCase {

    private SubscriptionPlanDao dao;

    public GetSubscriptionPlanByIdUseCase(SubscriptionPlanDao dao) {
        this.dao = dao;
    }

    public SubscriptionPlan apply(long id) {
        var plan = dao.findById(id);
//        if (plan == null) {
//            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Subscription plan not found");
//        }
        return plan;
    }

}
