package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class CountCartItemByUserUseCaseImpl implements CountCartItemByUserUseCase {

    private CartItemDao dao;

    public CountCartItemByUserUseCaseImpl(CartItemDao dao) {
        this.dao = dao;
    }

    @Override
    public long apply(long userId) {
        return dao.countByUser(userId);
    }

}
