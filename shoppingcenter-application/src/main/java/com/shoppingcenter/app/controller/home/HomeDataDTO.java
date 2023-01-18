package com.shoppingcenter.app.controller.home;

import java.util.List;

import com.shoppingcenter.app.controller.banner.dto.BannerDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeDataDTO {

    private List<BannerDTO> banners;

    private List<CategoryDTO> mainCategories;

    private List<ShopDTO> featuredShops;

}
