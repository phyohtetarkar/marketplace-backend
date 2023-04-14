package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopMember;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class CreateShopMemberUseCaseImpl implements CreateShopMemberUseCase {

    private ShopMemberDao dao;

    private ShopDao shopDao;

    private UserDao userDao;

    @Override
    public void apply(ShopMember member) {
        if (!shopDao.existsById(member.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        if (!userDao.existsById(member.getUserId())) {
            throw new ApplicationException("User not found");
        }
        dao.save(member);
    }

}
