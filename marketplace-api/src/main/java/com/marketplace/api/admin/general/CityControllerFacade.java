package com.marketplace.api.admin.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.api.consumer.general.CityDTO;
import com.marketplace.domain.general.City;
import com.marketplace.domain.general.usecase.DeleteCityUseCase;
import com.marketplace.domain.general.usecase.GetAllCityUseCase;
import com.marketplace.domain.general.usecase.SaveCityUseCase;

@Component
public class CityControllerFacade extends AbstractControllerFacade {

	@Autowired
	private SaveCityUseCase saveCityUseCase;
	
	@Autowired
	private DeleteCityUseCase deleteCityUseCase;
	
	@Autowired
	private GetAllCityUseCase getAllCityUseCase;
	
	public void save(CityDTO dto) {
		saveCityUseCase.apply(modelMapper.map(dto, City.class));
	}
	
	public void delete(long cityId) {
		deleteCityUseCase.apply(cityId);
	}
	
	public List<CityDTO> findAll() {
		return modelMapper.map(getAllCityUseCase.apply(), CityDTO.listType());
	}
}
