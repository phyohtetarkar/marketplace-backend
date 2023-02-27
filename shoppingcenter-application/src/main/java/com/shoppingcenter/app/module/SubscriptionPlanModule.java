package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;
import com.shoppingcenter.domain.subscription.usecase.DeleteSubscriptionPlanUseCase;
import com.shoppingcenter.domain.subscription.usecase.DeleteSubscriptionPlanUseCaseImpl;
import com.shoppingcenter.domain.subscription.usecase.GetAllSubscriptionPlanUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetAllSubscriptionPlanUseCaseImpl;
import com.shoppingcenter.domain.subscription.usecase.GetSubscriptionPlanByIdUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetSubscriptionPlanByIdUseCaseImpl;
import com.shoppingcenter.domain.subscription.usecase.SaveSubscriptionPlanUseCase;
import com.shoppingcenter.domain.subscription.usecase.SaveSubscriptionPlanUseCaseImpl;

@Configuration
public class SubscriptionPlanModule {

    @Autowired
    private SubscriptionPlanDao subscriptionPlanDao;

    @Bean
    SaveSubscriptionPlanUseCase saveSubscriptionPlanUseCase() {
        return new SaveSubscriptionPlanUseCaseImpl(subscriptionPlanDao);
    }

    @Bean
    DeleteSubscriptionPlanUseCase deleteSubscriptionPlanUseCase() {
        return new DeleteSubscriptionPlanUseCaseImpl(subscriptionPlanDao);
    }

    @Bean
    GetSubscriptionPlanByIdUseCase getSubscriptionPlanByIdUseCase() {
        return new GetSubscriptionPlanByIdUseCaseImpl(subscriptionPlanDao);
    }

    @Bean
    GetAllSubscriptionPlanUseCase getAllSubscriptionPlanUseCase() {
        return new GetAllSubscriptionPlanUseCaseImpl(subscriptionPlanDao);
    }
}
