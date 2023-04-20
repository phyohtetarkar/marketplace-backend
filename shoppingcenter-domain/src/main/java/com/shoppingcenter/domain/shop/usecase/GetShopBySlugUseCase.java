package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetShopBySlugUseCase {

    private ShopDao dao;

    public GetShopBySlugUseCase(ShopDao dao) {
        this.dao = dao;
    }

    public Shop apply(String slug) {
        var shop = dao.findBySlug(slug);
//        if (shop == null) {
//            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Shop not found");
//        }
        return shop;
    }

}
