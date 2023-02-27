package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;

public class DeleteSubscriptionPlanUseCaseImpl implements DeleteSubscriptionPlanUseCase {

    private SubscriptionPlanDao dao;

    public DeleteSubscriptionPlanUseCaseImpl(SubscriptionPlanDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(long id) {
        dao.delete(id);
    }

}
