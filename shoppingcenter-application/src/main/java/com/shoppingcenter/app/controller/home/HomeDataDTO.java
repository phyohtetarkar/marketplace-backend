package com.shoppingcenter.app.controller.home;

import java.util.List;

import com.shoppingcenter.core.banner.model.Banner;
import com.shoppingcenter.core.shop.model.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeDataDTO {

    private List<Banner> banners;

    private List<Shop> recommendedShops;

}
