package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.subscription.SubscriptionPlan;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;

public class GetSubscriptionPlanByIdUseCaseImpl implements GetSubscriptionPlanByIdUseCase {

    private SubscriptionPlanDao dao;

    public GetSubscriptionPlanByIdUseCaseImpl(SubscriptionPlanDao dao) {
        this.dao = dao;
    }

    @Override
    public SubscriptionPlan apply(long id) {
        var plan = dao.findById(id);
        if (plan == null) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Subscription plan not found");
        }
        return plan;
    }

}
