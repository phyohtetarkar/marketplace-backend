package com.marketplace.domain.general.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.general.City;
import com.marketplace.domain.general.CityDao;

@Component
public class GetAllCityUseCase {

	@Autowired
	private CityDao dao;

	public List<City> apply() {
		return dao.findAll();
	}
}
