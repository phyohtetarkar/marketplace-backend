package com.shoppingcenter.app.controller.home;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.banner.dto.BannerDTO;
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

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public HomeDataDTO getHome() {
        HomeDataDTO dto = new HomeDataDTO();
        dto.setBanners(modelMapper.map(bannerService.findAll(), BannerDTO.listType()));
        return dto;
    }

}
