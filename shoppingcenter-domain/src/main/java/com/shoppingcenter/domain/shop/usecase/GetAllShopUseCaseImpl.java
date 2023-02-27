package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetAllShopUseCaseImpl implements GetAllShopUseCase {

    private ShopDao dao;

    public GetAllShopUseCaseImpl(ShopDao dao) {
        this.dao = dao;
    }

    @Override
    public PageData<Shop> apply(ShopQuery query) {
        return dao.findAll(query);
    }

}
