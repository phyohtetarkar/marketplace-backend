package com.shoppingcenter.domain.shoppingcart.usecase;

import java.util.List;

import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shoppingcart.CartItem;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class GetCartItemsByUserUseCaseImpl implements GetCartItemsByUserUseCase {

    private CartItemDao dao;

    public GetCartItemsByUserUseCaseImpl(CartItemDao dao) {
        this.dao = dao;
    }

    @Override
    public List<CartItem> apply(String userId) {
        var items = dao.findByUser(userId);

        return items.stream().filter(v -> {
            var published = v.getProduct().getStatus() == Product.Status.PUBLISHED;
            var shopActive = v.getProduct().getShop().getStatus() == Shop.Status.ACTIVE;

            return published && shopActive;
        }).toList();
    }

}
