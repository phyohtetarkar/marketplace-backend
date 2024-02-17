package com.marketplace.data.general;

import com.marketplace.domain.general.City;

public interface CityMapper {

	public static City toDomain(CityEntity entity) {
		var city = new City();
		city.setId(entity.getId());
		city.setName(entity.getName());
		return city;
	}
	
}
