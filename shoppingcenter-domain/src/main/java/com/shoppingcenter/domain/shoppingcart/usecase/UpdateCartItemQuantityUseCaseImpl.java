package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shoppingcart.CartItem;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class UpdateCartItemQuantityUseCaseImpl implements UpdateCartItemQuantityUseCase {

    private CartItemDao dao;

    public UpdateCartItemQuantityUseCaseImpl(CartItemDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(CartItem item) {
        if (item.getQuantity() <= 0) {
            throw new ApplicationException("Quantity must not less than 1");
        }
        dao.save(item);
    }

}
