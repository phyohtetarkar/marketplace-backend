package com.marketplace.api.admin.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.api.consumer.general.CityDTO;
import com.marketplace.domain.general.usecase.DeleteCityUseCase;
import com.marketplace.domain.general.usecase.GetAllCityUseCase;
import com.marketplace.domain.general.usecase.SaveCityUseCase;

@Component
public class CityControllerFacade {

	@Autowired
	private SaveCityUseCase saveCityUseCase;
	
	@Autowired
	private DeleteCityUseCase deleteCityUseCase;
	
	@Autowired
	private GetAllCityUseCase getAllCityUseCase;
	
	@Autowired
    private AdminDataMapper adminMapper;
	
	@Autowired
	private ConsumerDataMapper consumerMapper;
	
	public void save(CityDTO dto) {
		saveCityUseCase.apply(adminMapper.map(dto));
	}
	
	public void delete(long cityId) {
		deleteCityUseCase.apply(cityId);
	}
	
	public List<CityDTO> findAll() {
		return consumerMapper.mapCityList(getAllCityUseCase.apply());
	}
}
