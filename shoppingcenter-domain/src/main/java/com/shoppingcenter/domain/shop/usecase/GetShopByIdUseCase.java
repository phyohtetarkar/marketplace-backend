package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetShopByIdUseCase {

    private ShopDao dao;

    public GetShopByIdUseCase(ShopDao dao) {
        this.dao = dao;
    }

    public Shop apply(long id) {
        Shop shop = dao.findById(id);
        if (shop == null) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Shop not found");
        }
        return shop;
    }

}
