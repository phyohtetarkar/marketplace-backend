package com.shoppingcenter.app.controller.misc;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.misc.dto.CityDTO;
import com.shoppingcenter.domain.misc.City;
import com.shoppingcenter.domain.misc.usecase.DeleteCityUseCase;
import com.shoppingcenter.domain.misc.usecase.GetAllCityUseCase;
import com.shoppingcenter.domain.misc.usecase.SaveCityUseCase;

import jakarta.transaction.Transactional;

@Facade
public class CityFacade {

	@Autowired
	private SaveCityUseCase saveCityUseCase;
	
	@Autowired
	private DeleteCityUseCase deleteCityUseCase;
	
	@Autowired
	private GetAllCityUseCase getAllCityUseCase;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public void save(CityDTO dto) {
		saveCityUseCase.apply(modelMapper.map(dto, City.class));
	}
	
	@Transactional
	public void delete(long cityId) {
		deleteCityUseCase.apply(cityId);
	}
	
	public List<CityDTO> findAll() {
		return modelMapper.map(getAllCityUseCase.apply(), CityDTO.listType());
	}
	
}
