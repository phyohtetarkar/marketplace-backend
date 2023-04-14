package com.shoppingcenter.domain.shoppingcart.usecase;

import java.util.List;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shoppingcart.CartItem;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class GetCartItemsByUserUseCaseImpl implements GetCartItemsByUserUseCase {

    private CartItemDao dao;

    public GetCartItemsByUserUseCaseImpl(CartItemDao dao) {
        this.dao = dao;
    }

    @Override
    public List<CartItem> apply(long userId) {
        var items = dao.findByUser(userId);

        return items.stream().filter(v -> {
            var available = !v.getProduct().isDisabled() && !v.getProduct().isHidden();
            var shopActive = v.getProduct().getShop().getStatus() == Shop.Status.ACTIVE;

            return available && shopActive;
        }).toList();
    }

}
