package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetShopByUserUseCaseImpl implements GetShopByUserUseCase {

    private ShopDao dao;

    public GetShopByUserUseCaseImpl(ShopDao dao) {
        this.dao = dao;
    }

    @Override
    public PageData<Shop> apply(String userId, Integer page) {
        return dao.findByUser(userId, Utils.normalizePage(page));
    }

}
