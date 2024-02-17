package com.marketplace.domain.general;

import java.util.List;

public interface CityDao {

	City save(City city);
	
	void deleteById(long cityId);
	
	boolean existsById(long id);
	
	City findById(long id);
	
	List<City> findAll();
	
}
