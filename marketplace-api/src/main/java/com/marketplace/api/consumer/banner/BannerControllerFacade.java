package com.marketplace.api.consumer.banner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.domain.banner.usecase.GetAllBannerUseCase;

@Component
public class BannerControllerFacade extends AbstractControllerFacade {

	@Autowired
	private GetAllBannerUseCase getAllBannerUseCase;

	public List<BannerDTO> findAll() {
		var source = getAllBannerUseCase.apply();
		return map(source, BannerDTO.listType());
	}
	
}
