package com.shoppingcenter.service.shoppingcart;

import java.util.List;

import com.shoppingcenter.service.shoppingcart.model.CartItem;

public interface CartItemService {

    void addToCart(CartItem item);

    CartItem updateQuantity(long id, int quantity);

    void removeFromCart(long id);

    void removeByUser(String userId);

    void removeAll(List<Long> ids);

    long countByUser(String userId);

    List<CartItem> findByUser(String userId);

}
