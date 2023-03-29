package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetShopByIdUseCaseImpl implements GetShopByIdUseCase {

    private ShopDao dao;

    public GetShopByIdUseCaseImpl(ShopDao dao) {
        this.dao = dao;
    }

    @Override
    public Shop apply(long id) {
        Shop shop = dao.findById(id);
        if (shop == null) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Shop not found");
        }
        return shop;
    }

}
