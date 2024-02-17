package com.marketplace.api.consumer.general;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.domain.general.usecase.GetAllCityUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/cities")
@Tag(name = "Consumer")
public class CityController {
	
	@Autowired
	private GetAllCityUseCase getAllCityUseCase;;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<CityDTO> getCities() {
		var source = getAllCityUseCase.apply();
		return modelMapper.map(source, CityDTO.listType());
	}
	
}
