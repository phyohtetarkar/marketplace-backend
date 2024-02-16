package com.marketplace.api.consumer.banner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/banners")
@Tag(name = "Consumer")
public class BannerController {

	@Autowired
	private BannerControllerFacade bannerFacade;
	
	
	@GetMapping
    public List<BannerDTO> getBanners() {
        return bannerFacade.findAll();
    }
	
}
