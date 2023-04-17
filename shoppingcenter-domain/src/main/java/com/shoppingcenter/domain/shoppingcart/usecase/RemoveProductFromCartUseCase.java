package com.shoppingcenter.domain.shoppingcart.usecase;

import java.util.List;

import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class RemoveProductFromCartUseCase {

    private CartItemDao dao;

    public RemoveProductFromCartUseCase(CartItemDao dao) {
        this.dao = dao;
    }

    public void apply(List<Long> items) {
        dao.deleteAll(items);
    }

}
