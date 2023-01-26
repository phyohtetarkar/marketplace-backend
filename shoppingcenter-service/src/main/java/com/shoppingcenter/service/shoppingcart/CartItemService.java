package com.shoppingcenter.service.shoppingcart;

import java.util.List;

import com.shoppingcenter.service.shoppingcart.model.CartItem;

public interface CartItemService {

    void addToCart(CartItem item);

    void updateQuantity(CartItem item);

    void removeFromCart(long id);

    void removeByUser(String userId);

    void removeAll(List<Long> ids);

    List<CartItem> findByUser(String userId);

}
