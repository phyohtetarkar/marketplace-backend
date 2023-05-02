package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.misc.CityDao;
import com.shoppingcenter.domain.misc.usecase.DeleteCityUseCase;
import com.shoppingcenter.domain.misc.usecase.GetAllCityUseCase;
import com.shoppingcenter.domain.misc.usecase.SaveCityUseCase;

@Configuration
public class MiscModule {

	@Autowired
	private CityDao cityDao;
	
	@Bean
	SaveCityUseCase saveCityUseCase() {
		return new SaveCityUseCase(cityDao);
	}
	
	@Bean
	DeleteCityUseCase deleteCityUseCase() {
		return new DeleteCityUseCase(cityDao);
	}
	
	@Bean
	GetAllCityUseCase getAllCityUseCase() {
		return new GetAllCityUseCase(cityDao);
	}
	
}
