package com.marketplace.domain.general.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.general.City;
import com.marketplace.domain.general.CityDao;

@Component
public class SaveCityUseCase {
	
	@Autowired
	private CityDao dao;

	public City apply(City city) {
		if (!Utils.hasText(city.getName())) {
			throw new ApplicationException("Required city name");
		}
		return dao.save(city);
	}
}
