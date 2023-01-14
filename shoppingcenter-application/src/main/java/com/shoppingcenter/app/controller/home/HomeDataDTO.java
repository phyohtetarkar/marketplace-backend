package com.shoppingcenter.app.controller.home;

import java.util.List;

import com.shoppingcenter.app.controller.banner.dto.BannerDTO;
import com.shoppingcenter.core.shop.model.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeDataDTO {

    private List<BannerDTO> banners;

    private List<Shop> recommendedShops;

}
