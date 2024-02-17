package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.subscription.SubscriptionPlan;
import com.marketplace.domain.subscription.SubscriptionPlanInput;
import com.marketplace.domain.subscription.dao.SubscriptionPlanDao;

@Component
public class SaveSubscriptionPlanUseCase {

	@Autowired
    private SubscriptionPlanDao dao;

    public SubscriptionPlan apply(SubscriptionPlanInput values) {
        if (!Utils.hasText(values.getTitle())) {
            throw new ApplicationException("Required plan title");
        }
        
        if (values.getDuration() <= 0) {
            throw new ApplicationException("Duraton must greater than zero");
        }
        
        if (values.getPrice() == null) {
            throw new ApplicationException("Required plan price");
        }
        
        return dao.save(values);
    }

}
