package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.order.usecase.GetPendingOrderCountByShopUseCase;

@Configuration
public class OrderModule {

	@Autowired
	private OrderDao orderDao;
	
	@Bean
    GetPendingOrderCountByShopUseCase getPendingOrderCountByShopUseCase() {
    	return new GetPendingOrderCountByShopUseCase(orderDao);
    }
	
}
