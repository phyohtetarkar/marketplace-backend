package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.subscription.SubscriptionPlan;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;

public class SaveSubscriptionPlanUseCaseImpl implements SaveSubscriptionPlanUseCase {

    private SubscriptionPlanDao dao;

    public SaveSubscriptionPlanUseCaseImpl(SubscriptionPlanDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(SubscriptionPlan plan) {
        if (!Utils.hasText(plan.getTitle())) {
            throw new ApplicationException("Plan title required");
        }
        dao.save(plan);
    }

}
