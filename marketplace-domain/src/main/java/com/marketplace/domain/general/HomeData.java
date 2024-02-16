package com.marketplace.domain.general;

import java.util.List;

import com.marketplace.domain.banner.Banner;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.shop.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeData {

	private List<Banner> banners;

    private List<Category> mainCategories;

    private List<Shop> featuredShops;
    
    private List<Product> featuredProducts;
    
    private List<Product> discountProducts;
    
}
