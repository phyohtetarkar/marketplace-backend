package com.shoppingcenter.domain.shoppingcart.usecase;

import java.util.List;

import com.shoppingcenter.domain.shoppingcart.CartItem;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class GetCartItemsByUserUseCaseImpl implements GetCartItemsByUserUseCase {

    private CartItemDao dao;

    public GetCartItemsByUserUseCaseImpl(CartItemDao dao) {
        this.dao = dao;
    }

    @Override
    public List<CartItem> apply(String userId) {
        return dao.findByUser(userId);
    }

}
