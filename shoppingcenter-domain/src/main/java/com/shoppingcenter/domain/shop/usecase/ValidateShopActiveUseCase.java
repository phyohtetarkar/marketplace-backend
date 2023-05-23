package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.Shop.Status;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class ValidateShopActiveUseCase {

    private ShopDao dao;

    public ValidateShopActiveUseCase(ShopDao dao) {
        this.dao = dao;
    }

    public void apply(long shopId) {

        var shop = dao.findById(shopId);

        if (shop == null) {
            throw new ApplicationException("Shop not found");
        }

        if (shop.getExpiredAt() <= System.currentTimeMillis()) {
            throw new ApplicationException("shop-subscription-required");
        }

        if (shop.getStatus() == Status.DISABLED) {
            throw new ApplicationException("shop-disabled");
        }
    }

}
