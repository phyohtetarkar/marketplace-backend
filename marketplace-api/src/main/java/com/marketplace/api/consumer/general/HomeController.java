package com.marketplace.api.consumer.general;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.domain.general.usecase.GetHomePageDataUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/home")
@Tag(name = "Consumer")
public class HomeController {
	
	@Autowired
	private GetHomePageDataUseCase getHomePageDataUseCase;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public HomeDataDTO getHome() {
		var source = getHomePageDataUseCase.apply();
		return modelMapper.map(source, HomeDataDTO.class);
	}

}
