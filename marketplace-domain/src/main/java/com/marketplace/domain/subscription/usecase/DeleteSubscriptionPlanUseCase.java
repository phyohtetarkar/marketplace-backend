package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.subscription.dao.SubscriptionPlanDao;

@Component
public class DeleteSubscriptionPlanUseCase {

	@Autowired
    private SubscriptionPlanDao dao;

    public void apply(long id) {
        dao.delete(id);
    }

}
