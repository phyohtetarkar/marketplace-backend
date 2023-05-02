package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.sale.SaleHistoryDao;
import com.shoppingcenter.domain.sale.usecase.GetMonthlySaleByShopUseCase;

@Configuration
public class SaleModule {

	@Autowired
	private SaleHistoryDao saleHistoryDao;
	
	@Bean
	GetMonthlySaleByShopUseCase getMonthlySaleByShopUseCase() {
		return new GetMonthlySaleByShopUseCase(saleHistoryDao);
	}
	
}
