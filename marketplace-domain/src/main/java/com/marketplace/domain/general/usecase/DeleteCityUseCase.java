package com.marketplace.domain.general.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.general.CityDao;

@Component
public class DeleteCityUseCase {

	@Autowired
	private CityDao dao;

	@Transactional
	public void apply(long cityId) {
		dao.deleteById(cityId);
	}
}
