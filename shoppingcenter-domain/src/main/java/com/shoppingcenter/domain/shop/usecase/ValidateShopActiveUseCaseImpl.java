package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class ValidateShopActiveUseCaseImpl implements ValidateShopActiveUseCase {

    private ShopDao dao;

    public ValidateShopActiveUseCaseImpl(ShopDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(long shopId) {
        if (!dao.existsById(shopId)) {
            throw new ApplicationException("Shop not found");
        }
        var status = dao.getStatus(shopId);

        if (Shop.Status.SUBSCRIPTION_EXPIRED.equals(status)) {
            throw new ApplicationException("shop-subscription-expired");
        }

        if (Shop.Status.DISABLED.equals(status)) {
            throw new ApplicationException("shop-disabled");
        }
    }

}
