package com.shoppingcenter.domain.misc.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.misc.City;
import com.shoppingcenter.domain.misc.CityDao;

public class SaveCityUseCase {
	
	private CityDao dao;
	
	public SaveCityUseCase(CityDao dao) {
		super();
		this.dao = dao;
	}

	public void apply(City city) {
		if (!Utils.hasText(city.getName())) {
			throw new ApplicationException("Required city name");
		}
		dao.save(city);
	}
}
