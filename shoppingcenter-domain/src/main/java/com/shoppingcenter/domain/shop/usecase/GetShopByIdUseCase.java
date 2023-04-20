package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetShopByIdUseCase {

    private ShopDao dao;

    public GetShopByIdUseCase(ShopDao dao) {
        this.dao = dao;
    }

    public Shop apply(long id) {
        var shop = dao.findById(id);
        return shop;
    }

}
