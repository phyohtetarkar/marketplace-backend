package com.marketplace.api.consumer.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.consumer.banner.BannerControllerFacade;
import com.marketplace.api.consumer.category.CategoryControllerFacade;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/home")
@Tag(name = "Consumer")
public class HomeController {

	@Autowired
	private BannerControllerFacade bannerFacade;

	@Autowired
	private CategoryControllerFacade categoryFacade;

	@GetMapping
	public HomeDataDTO getHome() {
		HomeDataDTO dto = new HomeDataDTO();
		dto.setBanners(bannerFacade.findAll());
		dto.setMainCategories(categoryFacade.getRootCategories());
		return dto;
	}

}
