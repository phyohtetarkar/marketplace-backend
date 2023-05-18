package com.shoppingcenter.domain.subscription.usecase;

import java.util.List;

import com.shoppingcenter.domain.subscription.SubscriptionPlan;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;

public class GetAllSubscriptionPlanUseCase {

    private SubscriptionPlanDao dao;

    public GetAllSubscriptionPlanUseCase(SubscriptionPlanDao dao) {
        this.dao = dao;
    }

    public List<SubscriptionPlan> apply() {
        return dao.findAll();
    }

}
