package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.ErrorCodes;
import com.marketplace.domain.shop.Shop.Status;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class ValidateShopActiveUseCase {

	@Autowired
    private ShopDao dao;

    public void apply(long shopId) {

        var shop = dao.findById(shopId);

        if (shop == null || shop.isDeleted()) {
            throw new ApplicationException(ErrorCodes.FORBIDDEN, "Shop not found");
        }

        if (shop.getExpiredAt() <= System.currentTimeMillis()) {
            throw new ApplicationException(ErrorCodes.FORBIDDEN, "shop-subscription-required");
        }

        if (shop.getStatus() == Status.DISABLED) {
            throw new ApplicationException(ErrorCodes.FORBIDDEN, "shop-disabled");
        }
    }

}
