package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.payment.PaymentGatewayAdapter;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionTransactionDao;
import com.shoppingcenter.domain.shop.usecase.CompleteShopSubscriptionUseCase;
import com.shoppingcenter.domain.shop.usecase.GetCurrentSubscriptionByShopUseCase;
import com.shoppingcenter.domain.shop.usecase.GetPreSubscriptionsByShopUseCase;
import com.shoppingcenter.domain.shop.usecase.RemoveUnprocessedSubscriptionsUseCase;
import com.shoppingcenter.domain.shop.usecase.RenewShopSubscriptionUseCase;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;
import com.shoppingcenter.domain.subscription.usecase.DeleteSubscriptionPlanUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetAllSubscriptionPlanUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetSubscriptionPlanByIdUseCase;
import com.shoppingcenter.domain.subscription.usecase.SaveSubscriptionPlanUseCase;

@Configuration
public class SubscriptionModule {

    @Autowired
    private SubscriptionPlanDao subscriptionPlanDao;
    
    @Autowired
    private ShopDao shopDao;
    
    @Autowired
    private ShopSubscriptionDao shopSubscriptionDao;
    
    @Autowired
    private ShopSubscriptionTransactionDao shopSubscriptionTransactionDao;
    
    @Autowired
    private ShopMemberDao shopMemberDao;
    
    @Autowired
    private PaymentGatewayAdapter paymentGatewayAdapter;

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
    
    @Bean
    RenewShopSubscriptionUseCase renewShopSubscriptionUseCase() {
    	var usecase = new RenewShopSubscriptionUseCase();
    	usecase.setShopDao(shopDao);
    	usecase.setShopSubscriptionDao(shopSubscriptionDao);
    	usecase.setSubscriptionPlanDao(subscriptionPlanDao);
    	usecase.setShopMemberDao(shopMemberDao);
    	usecase.setPaymentGatewayAdapter(paymentGatewayAdapter);
    	return usecase;
    }
    
    @Bean
    CompleteShopSubscriptionUseCase completeShopSubscriptionUseCase() {
    	var usecase = new CompleteShopSubscriptionUseCase();
    	usecase.setShopDao(shopDao);
    	usecase.setShopSubscriptionDao(shopSubscriptionDao);
    	usecase.setShopSubscriptionTransactionDao(shopSubscriptionTransactionDao);
    	return usecase;
    }
    
    @Bean
    GetCurrentSubscriptionByShopUseCase getCurrentSubscriptionByShopUseCase() {
    	return new GetCurrentSubscriptionByShopUseCase(shopSubscriptionDao);
    }
    
    @Bean
    GetPreSubscriptionsByShopUseCase getPreSubscriptionsByShopUseCase() {
    	return new GetPreSubscriptionsByShopUseCase(shopSubscriptionDao);
    }
    
    @Bean
    RemoveUnprocessedSubscriptionsUseCase removeUnprocessedSubscriptionsUseCase() {
    	return new RemoveUnprocessedSubscriptionsUseCase(shopSubscriptionDao);
    }
}
