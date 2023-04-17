package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class UpdateCartItemQuantityUseCase {

    private CartItemDao dao;

    public UpdateCartItemQuantityUseCase(CartItemDao dao) {
        this.dao = dao;
    }

    public void apply(long id, int quantity) {
        if (!dao.existsById(id)) {
            throw new ApplicationException("Item not found");
        }
        if (quantity <= 0) {
            throw new ApplicationException("Quantity must not less than 1");
        }
        dao.update(id, quantity);
    }

}
