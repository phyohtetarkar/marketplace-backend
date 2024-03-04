package com.marketplace.api.consumer.banner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.domain.banner.usecase.GetAllBannerUseCase;

@Component
public class BannerControllerFacade {

	@Autowired
	private GetAllBannerUseCase getAllBannerUseCase;
	
	@Autowired
	private ConsumerDataMapper mapper;

	public List<BannerDTO> findAll() {
		var source = getAllBannerUseCase.apply();
		return mapper.mapBannerList(source);
	}
	
}
