package com.shoppingcenter.core.shoppingcart;

import java.util.List;

import com.shoppingcenter.core.shoppingcart.model.CartItem;

public interface CartItemService {

    void addToCart(CartItem item);

    void updateQuantity(CartItem item);

    void removeFromCart(String id);

    void removeByUser(String userId);

    void removeAll(List<String> ids);

    List<CartItem> findByUser(String userId);

}
