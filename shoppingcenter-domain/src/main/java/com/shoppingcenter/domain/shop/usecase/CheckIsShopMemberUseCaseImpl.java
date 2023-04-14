package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

public class CheckIsShopMemberUseCaseImpl implements CheckIsShopMemberUseCase {

    private ShopMemberDao dao;

    public CheckIsShopMemberUseCaseImpl(ShopMemberDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean apply(long shopId, long userId) {
        return dao.existsByShopAndUser(shopId, userId);
    }

}
