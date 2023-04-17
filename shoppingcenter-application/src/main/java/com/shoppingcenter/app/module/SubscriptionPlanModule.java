package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;
import com.shoppingcenter.domain.subscription.usecase.DeleteSubscriptionPlanUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetAllSubscriptionPlanUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetSubscriptionPlanByIdUseCase;
import com.shoppingcenter.domain.subscription.usecase.SaveSubscriptionPlanUseCase;

@Configuration
public class SubscriptionPlanModule {

    @Autowired
    private SubscriptionPlanDao subscriptionPlanDao;

    @Bean
    SaveSubscriptionPlanUseCase saveSubscriptionPlanUseCase() {
        return new SaveSubscriptionPlanUseCase(subscriptionPlanDao);
    }

    @Bean
    DeleteSubscriptionPlanUseCase deleteSubscriptionPlanUseCase() {
        return new DeleteSubscriptionPlanUseCase(subscriptionPlanDao);
    }

    @Bean
    GetSubscriptionPlanByIdUseCase getSubscriptionPlanByIdUseCase() {
        return new GetSubscriptionPlanByIdUseCase(subscriptionPlanDao);
    }

    @Bean
    GetAllSubscriptionPlanUseCase getAllSubscriptionPlanUseCase() {
        return new GetAllSubscriptionPlanUseCase(subscriptionPlanDao);
    }
}
