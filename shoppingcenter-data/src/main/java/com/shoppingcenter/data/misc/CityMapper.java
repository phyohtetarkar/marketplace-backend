package com.shoppingcenter.data.misc;

import com.shoppingcenter.domain.misc.City;

public class CityMapper {

	public static City toDomain(CityEntity entity) {
		var city = new City();
		city.setId(entity.getId());
		city.setName(entity.getName());
		return city;
	}
	
}
