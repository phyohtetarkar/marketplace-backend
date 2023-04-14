package com.shoppingcenter.domain.shoppingcart.usecase;

import java.util.List;

import com.shoppingcenter.domain.shoppingcart.CartItem;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class RemoveProductFromCartUseCaseImpl implements RemoveProductFromCartUseCase {

    private CartItemDao dao;

    public RemoveProductFromCartUseCaseImpl(CartItemDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(List<CartItem> items) {
        dao.deleteAll(items);
    }

}
