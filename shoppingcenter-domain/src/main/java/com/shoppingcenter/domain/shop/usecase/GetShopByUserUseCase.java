package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetShopByUserUseCase {

    private ShopDao dao;

    public GetShopByUserUseCase(ShopDao dao) {
        this.dao = dao;
    }

    public PageData<Shop> apply(long userId, Integer page) {
        return dao.findByUser(userId, Utils.normalizePage(page));
    }

}
