package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

public class ValidateShopMemberUseCase {

    private ShopMemberDao dao;

    public ValidateShopMemberUseCase(ShopMemberDao dao) {
        this.dao = dao;
    }

    public void apply(long shopId, long userId) {
        if (!dao.existsByShopAndUser(shopId, userId)) {
            throw new ApplicationException("permission-denied");
        }
    }

}
