package com.marketplace.domain.subscription.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.subscription.SubscriptionPlan;
import com.marketplace.domain.subscription.dao.SubscriptionPlanDao;

@Component
public class GetAllSubscriptionPlanUseCase {

	@Autowired
    private SubscriptionPlanDao dao;

    public List<SubscriptionPlan> apply() {
        return dao.findAll();
    }

}
