package com.shoppingcenter.domain.misc.usecase;

import com.shoppingcenter.domain.misc.CityDao;

public class DeleteCityUseCase {

	private CityDao dao;

	public DeleteCityUseCase(CityDao dao) {
		super();
		this.dao = dao;
	}

	public void apply(long cityId) {
		dao.deleteById(cityId);
	}
}
