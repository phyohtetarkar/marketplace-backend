package com.shoppingcenter.domain.shoppingcart.usecase;

import java.util.List;

import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.shop.Shop.Status;
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
        	var shop = v.getProduct().getShop();
            var available = !v.getProduct().isDisabled() && v.getProduct().getStatus() == Product.Status.PUBLISHED;
            var shopActive = shop.getStatus() == Status.APPROVED && shop.getExpiredAt() >= System.currentTimeMillis();

            return available && shopActive;
        }).toList();
    }

}
