package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

public class CheckIsShopMemberUseCase {

    private ShopMemberDao dao;

    public CheckIsShopMemberUseCase(ShopMemberDao dao) {
        this.dao = dao;
    }

    public boolean apply(long shopId, long userId) {
        return dao.existsByShopAndUser(shopId, userId);
    }

}
