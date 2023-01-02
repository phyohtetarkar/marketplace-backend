package com.shoppingcenter.app.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.core.banner.BannerService;
import com.shoppingcenter.core.shop.ShopQueryService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/home")
@Tag(name = "Home")
public class HomeController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ShopQueryService shopQueryService;

    @GetMapping
    public HomeDataDTO getHome() {
        HomeDataDTO dto = new HomeDataDTO();
        dto.setBanners(bannerService.findAll());
        return dto;
    }

}
