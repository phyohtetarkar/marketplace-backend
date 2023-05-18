package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.shop.dao.ShopMonthlySaleDao;
import com.shoppingcenter.domain.shop.usecase.GetMonthlySaleByShopUseCase;

@Configuration
public class SaleModule {

	@Autowired
	private ShopMonthlySaleDao shopMonthlySaleDao;
	
	@Bean
	GetMonthlySaleByShopUseCase getMonthlySaleByShopUseCase() {
		return new GetMonthlySaleByShopUseCase(shopMonthlySaleDao);
	}
	
}
