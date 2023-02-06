package com.shoppingcenter.service.shoppingcart;

import java.util.List;

import com.shoppingcenter.service.shoppingcart.model.CartItem;

public interface CartItemService {

    void addToCart(CartItem item);

    CartItem updateQuantity(long id, int quantity, String userId);

    void removeFromCart(String userId, long id);

    void removeByUser(String userId);

    void removeAll(String userId, List<Long> ids);

    List<CartItem> findByUser(String userId);

}
