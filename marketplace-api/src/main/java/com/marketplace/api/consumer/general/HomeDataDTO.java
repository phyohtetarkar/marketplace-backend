package com.marketplace.api.consumer.general;

import java.util.List;

import com.marketplace.api.consumer.banner.BannerDTO;
import com.marketplace.api.consumer.category.CategoryDTO;
import com.marketplace.api.consumer.product.ProductDTO;
import com.marketplace.api.consumer.shop.ShopDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeDataDTO {

    private List<BannerDTO> banners;

    private List<CategoryDTO> mainCategories;

    private List<ShopDTO> featuredShops;
    
    private List<ProductDTO> featuredProducts;
    
    private List<ProductDTO> discountProducts;

}
