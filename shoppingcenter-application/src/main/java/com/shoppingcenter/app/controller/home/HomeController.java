package com.shoppingcenter.app.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.banner.BannerFacade;
import com.shoppingcenter.app.controller.category.CategoryFacade;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/home")
@Tag(name = "Home")
public class HomeController {

    @Autowired
    private BannerFacade bannerFacade;

    @Autowired
    private CategoryFacade categoryFacade;

    @GetMapping
    public HomeDataDTO getHome() {
        HomeDataDTO dto = new HomeDataDTO();
        dto.setBanners(bannerFacade.findAll());
        dto.setMainCategories(categoryFacade.findRootCategories());
        return dto;
    }

}
