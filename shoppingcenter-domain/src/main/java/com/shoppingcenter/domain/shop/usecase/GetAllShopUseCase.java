package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetAllShopUseCase {

    private ShopDao dao;

    public GetAllShopUseCase(ShopDao dao) {
        this.dao = dao;
    }

    public PageData<Shop> apply(ShopQuery query) {
        return dao.getShops(query);
    }

}
