package com.shoppingcenter.domain.shoppingcart.usecase;

import java.util.List;

import com.shoppingcenter.domain.shoppingcart.CartItem;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class GetCartItemsByUserUseCase {

    private CartItemDao dao;

    public GetCartItemsByUserUseCase(CartItemDao dao) {
        this.dao = dao;
    }

    public List<CartItem> apply(long userId) {
        var items = dao.findByUser(userId);

        return items.stream().filter(v -> {
            var available = !v.getProduct().isDisabled() && !v.getProduct().isHidden();
            var shopActive = v.getProduct().getShop().isActivated();

            return available && shopActive;
        }).toList();
    }

}
