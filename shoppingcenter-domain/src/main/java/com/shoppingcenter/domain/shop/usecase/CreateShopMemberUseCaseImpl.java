package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.ShopMember;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

public class CreateShopMemberUseCaseImpl implements CreateShopMemberUseCase {

    private ShopMemberDao dao;

    public CreateShopMemberUseCaseImpl(ShopMemberDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(ShopMember member) {
        dao.save(member);
    }

}
