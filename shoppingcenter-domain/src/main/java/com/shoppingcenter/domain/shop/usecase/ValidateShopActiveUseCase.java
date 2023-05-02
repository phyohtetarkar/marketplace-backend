package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class ValidateShopActiveUseCase {

    private ShopDao dao;

    public ValidateShopActiveUseCase(ShopDao dao) {
        this.dao = dao;
    }

    public void apply(long shopId) {

        var shop = dao.findById(shopId);

        if (shop == null || !shop.isActivated()) {
            throw new ApplicationException("Shop not found");
        }

        if (shop.isExpired()) {
            throw new ApplicationException("shop-subscription-expired");
        }

        if (shop.isDisabled()) {
            throw new ApplicationException("shop-disabled");
        }
    }

}
