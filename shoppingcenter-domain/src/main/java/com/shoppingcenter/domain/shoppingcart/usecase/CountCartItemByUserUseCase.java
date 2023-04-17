package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class CountCartItemByUserUseCase {

    private CartItemDao dao;

    public CountCartItemByUserUseCase(CartItemDao dao) {
        this.dao = dao;
    }

    public long apply(long userId) {
        return dao.countByUser(userId);
    }

}
