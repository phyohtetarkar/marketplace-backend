package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.domain.shop.usecase.CheckIsShopMemberUseCase;

@Facade
public class ShopMemberFacade {

    @Autowired
    private CheckIsShopMemberUseCase checkIsShopMemberUseCase;

    public boolean isMember(long shopId, long userId) {
        return checkIsShopMemberUseCase.apply(shopId, userId);
    }

}
