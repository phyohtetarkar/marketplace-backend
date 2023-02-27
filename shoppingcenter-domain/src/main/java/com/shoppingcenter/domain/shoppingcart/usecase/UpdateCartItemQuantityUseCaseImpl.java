package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class UpdateCartItemQuantityUseCaseImpl implements UpdateCartItemQuantityUseCase {

    private CartItemDao dao;

    public UpdateCartItemQuantityUseCaseImpl(CartItemDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(long itemId, int quantity) {
        if (quantity <= 0) {
            throw new ApplicationException("Quantity must not less than 1");
        }
        dao.updateQuantity(itemId, quantity);
    }

}
