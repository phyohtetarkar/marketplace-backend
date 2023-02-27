package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.domain.shop.usecase.CheckIsShopMemberUseCase;

@Facade
public class ShopMemberFacadeImpl implements ShopMemberFacade {

    @Autowired
    private CheckIsShopMemberUseCase checkIsShopMemberUseCase;

    @Override
    public boolean isMember(long shopId, String userId) {
        return checkIsShopMemberUseCase.apply(shopId, userId);
    }

}
