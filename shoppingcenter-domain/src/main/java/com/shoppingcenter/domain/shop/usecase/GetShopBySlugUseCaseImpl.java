package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetShopBySlugUseCaseImpl implements GetShopBySlugUseCase {

    private ShopDao dao;

    public GetShopBySlugUseCaseImpl(ShopDao dao) {
        this.dao = dao;
    }

    @Override
    public Shop apply(String slug) {
        Shop shop = dao.findBySlug(slug);
        if (shop == null) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Shop not found");
        }
        return shop;
    }

}
