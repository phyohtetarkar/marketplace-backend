package com.shoppingcenter.domain.misc;

import java.util.List;

public interface CityDao {

	void save(City city);
	
	void deleteById(long cityId);
	
	List<City> findAll();
	
}
