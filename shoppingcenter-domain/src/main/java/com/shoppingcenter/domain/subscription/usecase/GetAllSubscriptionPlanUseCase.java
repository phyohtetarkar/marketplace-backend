package com.shoppingcenter.domain.subscription.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.subscription.SubscriptionPlan;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;

public class GetAllSubscriptionPlanUseCase {

    private SubscriptionPlanDao dao;

    public GetAllSubscriptionPlanUseCase(SubscriptionPlanDao dao) {
        this.dao = dao;
    }

    public PageData<SubscriptionPlan> apply(Integer page) {
        return dao.findAll(Utils.normalizePage(page));
    }

}
