package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;

public class DeleteSubscriptionPlanUseCase {

    private SubscriptionPlanDao dao;

    public DeleteSubscriptionPlanUseCase(SubscriptionPlanDao dao) {
        this.dao = dao;
    }

    public void apply(long id) {
        dao.delete(id);
    }

}
