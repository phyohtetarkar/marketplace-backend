package com.shoppingcenter.domain.misc;

import java.util.List;

public interface CityDao {

	void save(City city);
	
	void deleteById(long cityId);
	
	boolean existsById(long id);
	
	City findById(long id);
	
	List<City> findAll();
	
}
